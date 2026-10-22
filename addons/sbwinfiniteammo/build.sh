#!/bin/bash
# Builds sbwinfiniteammo-1.0.0.jar with plain javac. The mixins target Superb Warfare's own (unobfuscated) classes
# with remap=false, and Minecraft is only referenced by class name, so no remapping is needed.
# usage: build.sh <forge-server-libraries-dir> <official-superbwarfare-jar>
set -euo pipefail
LIBS="$1"
SBW="$2"
HERE="$(cd "$(dirname "$0")" && pwd)"
JDK="/c/Program Files/Eclipse Adoptium/jdk-17.0.18.8-hotspot/bin"
CP="$SBW"
for j in net/minecraftforge/javafmllanguage/1.20.1-47.4.10/javafmllanguage-1.20.1-47.4.10.jar \
         net/minecraftforge/fmlcore/1.20.1-47.4.10/fmlcore-1.20.1-47.4.10.jar \
         net/minecraftforge/forge/1.20.1-47.4.10/forge-1.20.1-47.4.10-universal.jar \
         net/minecraftforge/eventbus/6.0.5/eventbus-6.0.5.jar \
         org/spongepowered/mixin/0.8.5/mixin-0.8.5.jar; do
  CP="$CP;$LIBS/$j"
done
for j in "$LIBS"/net/minecraft/server/*/server-*-srg.jar "$LIBS"/org/slf4j/slf4j-api/*/slf4j-api-*.jar \
         "$LIBS"/com/electronwill/night-config/core/*/core-*.jar; do
  CP="$CP;$j"
done

rm -rf "$HERE/build"
mkdir -p "$HERE/build/classes"
"$JDK/javac.exe" --release 17 -proc:none -Xlint:all,-classfile,-processing -cp "$CP" -d "$HERE/build/classes" \
  "$HERE/src/com/hans/sbwinfiniteammo/SbwInfiniteAmmo.java" \
  "$HERE/src/com/hans/sbwinfiniteammo/mixin/InventoryToolMixin.java" \
  "$HERE/src/com/hans/sbwinfiniteammo/mixin/GunDataMixin.java"
cp -r "$HERE/resources/." "$HERE/build/classes/"
printf 'Manifest-Version: 1.0\nMixinConfigs: sbwinfiniteammo.mixins.json\nImplementation-Title: sbwinfiniteammo\nImplementation-Version: 1.0.0\n' > "$HERE/build/MANIFEST.MF"
"$JDK/jar.exe" --create --file "$HERE/build/sbwinfiniteammo-1.0.0.jar" --manifest "$HERE/build/MANIFEST.MF" -C "$HERE/build/classes" .
echo "built $HERE/build/sbwinfiniteammo-1.0.0.jar"
