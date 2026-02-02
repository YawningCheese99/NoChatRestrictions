# NoChatRestrictions
# DO NOT USE THIS!! USE NoChatRestrictions2
A Java agent that removes the chat restrictions in Minecraft.

This is NOT compatible with all of the affected versions (1.16.4 and onwards), it was made for the latest version of Minecraft. Support for older versions may be released in the future.

How to use:
add ```-javaagent:path/to/jar/nochatrestrictions.jar``` to your Minecraft command line arguments.

This is completely untested, and may not work at all. It is based off of the similar Minecraft mod made for mod loaders.
Compatible with unmodified clients, Fabric and Forge.

This Java agent targets the Minecraft authentication library, which is kept separate from the base client and is unobfuscated. This way, it should support future versions and doesn't rely on code within Minecraft itself, so it should work with any mods/modloaders.
