SUMMARY = "LibreSSL is a version of the TLS/crypto stack forked from OpenSSL in 2014"
DESCRIPTION = "LibreSSL is a version of the TLS/crypto stack forked from OpenSSL \
in 2014, with goals of modernizing the codebase, improving security, and applying \
best practice development processes."
HOMEPAGE = "https://www.libressl.org/"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://libtls-standalone/COPYING;md5=4b08422df062b2dffb6239413973dc98"

SECTION = "libs"

PROVIDES += "openssl"
RREPLACES_${PN} = "openssl"
RREPLACES_${PN}-bin = "openssl-bin"
RREPLACES_${PN}-locale = "openssl-locale"
RREPLACES_${PN}-dev = "openssl-dev"
RREPLACES_${PN}-dbg = "openssl-dbg"
RREPLACES_${PN}-ptest = "openssl-ptest"
RREPLACES_${PN}-src = "openssl-src"
PROVIDES_class-nativesdk = "nativesdk-openssl"
RREPLACES_${PN}_class-nativesdk = "nativesdk-openssl"
RREPLACES_${PN}-bin_class-nativesdk = "nativesdk-openssl-bin"

SRC_URI = "git://github.com/libressl-portable/portable.git;protocol=https;nobranch=1"
SRCREV = "11b1c765b89668009d497c562bc1e1fb70b621f2"
S = "${WORKDIR}/git"

inherit autotools lib_package ptest

do_configure_prepend () {
	cd "${S}"
	./update.sh
	cd "${B}"
}

do_install_append_class-nativesdk () {
	mkdir -p ${D}${SDKPATHNATIVE}/environment-setup.d
	cat <<EOF >${WORKDIR}/environment.d-libressl.sh
export OPENSSL_CONF="$OECORE_NATIVE_SYSROOT/etc/ssl/openssl.cnf"
EOF
	install -m 644 ${WORKDIR}/environment.d-libressl.sh ${D}${SDKPATHNATIVE}/environment-setup.d/libressl.sh
}

PACKAGES =+ "libcrypto libssl openssl-conf libressl-ptest"

FILES_libcrypto = "${libdir}/libcrypto${SOLIBS}"
FILES_libssl = "${libdir}/libssl${SOLIBS}"
FILES_openssl-conf = "${sysconfdir}/ssl/openssl.cnf"
FILES_${PN}_append_class-nativesdk = " ${SDKPATHNATIVE}/environment-setup.d/openssl.sh"

CONFFILES_openssl-conf = "${sysconfdir}/ssl/openssl.cnf"

RRECOMMENDS_libcrypto += "openssl-conf"
RDEPENDS_${PN}-ptest += "${PN}-bin perl perl-modules bash"

RDEPENDS_${PN}-bin += "openssl-conf"

BBCLASSEXTEND += "native nativesdk"
