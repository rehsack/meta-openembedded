require llvm.inc

# 3-clause BSD-like
# University of Illinois/NCSA Open Source License
LICENSE = "NCSA"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=47e311aa9caedd1b3abf098bd7814d1d"

DEPENDS += "zlib"
EXTRA_OECONF += "--enable-zlib"

SRC_URI_append_libc-uclibc = " file://arm_fenv_uclibc.patch "

SRC_URI[md5sum] = "f5a4dc595f7e8bd23397684d0906d014"
SRC_URI[sha256sum] = "44196156d5749eb4b4224fe471a29cc3984df92570a4a89fa859f7394fc0c575"

PACKAGECONFIG ??= ""
PACKAGECONFIG[r600] = "--enable-experimental-targets=R600,,,"

# Fails to build with thumb-1 (qemuarm)
# | {standard input}: Assembler messages:
# | {standard input}:22: Error: selected processor does not support Thumb mode `stmdb sp!,{r0,r1,r2,r3,lr}'
# | {standard input}:31: Error: lo register required -- `ldmia sp!,{r0,r1,r2,r3,lr}'
# | {standard input}:32: Error: lo register required -- `ldr pc,[sp],#4'
# | make[3]: *** [/home/jenkins/oe/world/shr-core/tmp-glibc/work/armv5te-oe-linux-gnueabi/llvm3.3/3.3-r0/llvm-3.3.build/lib/Target/ARM/Release/ARMJITInfo.o] Error 1
ARM_INSTRUCTION_SET = "arm"
