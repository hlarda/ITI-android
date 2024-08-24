SUMMARY = "Led Ping Indicator"
DESCRIPTION = "Led Ping Indicator Recipe"
LICENSE = "CLOSED"

# Add bash as a runtime dependency
RDEPENDS:${PN} += " bash"

# Script to be installed
SRC_URI = "file://bingLedIndicator.sh"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/bingLedIndicator.sh ${D}${bindir}/bingLedIndicator
}
