//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package webvisca.cmd;
public final class PanTiltDownCmd extends Cmd {
    private static final byte[] ptUpCommandData = new byte[]{1, 1, 1, 0, 0, 3, 2};

    public PanTiltDownCmd() {
    }

    public byte[] createCommandData() {
        byte[] cmdData = duplicatArray(ptUpCommandData);
        cmdData[3] = 1;
        cmdData[4] = 2;
        return cmdData;
    }

    private static byte[] duplicatArray(byte[] src) {
        byte[] dest = new byte[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }
}
