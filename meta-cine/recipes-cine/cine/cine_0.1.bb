SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "CLOSED"



SRC_URI = "file://emotion-1.0.tar.xz"
S = "${WORKDIR}"


do_install() {
install -d ${D}${bindir}
install -m 0755 emotions.py ${D}${bindir}
install -m 0755 haarcascade_frontalface_default.xml ${D}${bindir}
install -m 0755 model.h5 ${D}${bindir}
}

python do_display_banner() {
    bb.plain("**********************************");
    bb.plain("*  Program created by:           *");
    bb.plain("*  Angelo Isaac Bonilla          *");
    bb.plain("*  Frander Díaz Ureña            *");
    bb.plain("*  Pablo Chaves Alfaro           *");
    bb.plain("*  Steven Rojas Cubero           *");
    bb.plain("**********************************");
}

addtask display_banner before do_build
