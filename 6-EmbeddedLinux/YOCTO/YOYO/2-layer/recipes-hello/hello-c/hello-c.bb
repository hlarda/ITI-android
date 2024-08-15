DESCRIPTION = "A simple example of C a bitbake recipe"
LICENSE = "CLOSED"
SRC_URI = "file://main.c"
S = "${WORKDIR}"

do_compile () {
    bbwarn "Compiling hello.c with ${CC} compiler"
    bbwarn "LDFLAGS: ${LDFLAGS}"
    # LDFLAGS is a variable that is used to pass flags to the linker
    ${CC} ${LDFLAGS} main.c -o hello -DCONFIG_VALUE=10
}

do_install () {
    install -d ${D}${bindir}
    bbwarn "${D}${bindir}"
    install -m 0755 ${S}/hello ${D}${bindir}
}
