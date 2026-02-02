package com.fix.legacy;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import org.objectweb.asm.*;

public class FixAgent {
    public static String exceptionString = "com/mojang/authlib/exceptions/AuthenticationException";
    public static String serviceString = "com/mojang/authlib/yggdrasil/YggdrasilUserApiService";
    public static String methodString = "fetchProperties";

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                
                if (serviceString.equals(className)) {
                    try {
                        ClassReader cr = new ClassReader(classfileBuffer);
                        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
                        
                        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
                            @Override
                            public MethodVisitor visitMethod(int access, String name, String descriptor, 
                                                           String signature, String[] exceptions) {
                                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                                
                                if (methodString.equals(name)) {
                                    System.out.println("CHAT CONTROL REMOVED");
                                    return new ThrowingMethodVisitor(mv);
                                }
                                return mv;
                            }
                        };
                        
                        cr.accept(cv, 0);
                        return cw.toByteArray();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
    }

    static class ThrowingMethodVisitor extends MethodVisitor {
        public ThrowingMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM9, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            super.visitTypeInsn(Opcodes.NEW, exceptionString);
            super.visitInsn(Opcodes.DUP);
            super.visitMethodInsn(Opcodes.INVOKESPECIAL, exceptionString, "<init>", "()V", false);
            super.visitInsn(Opcodes.ATHROW);
        }
    }
}
