package webvisca;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import webvisca.cmd.*;

import javax.annotation.PostConstruct;

@Service
public class CommandService {

    private final Logger logger = LoggerFactory.getLogger(CommandService.class);

    private SerialPort serialPort;

    @PostConstruct
    private void postConstruct(){

        String com = "/dev/ttyUSB0";
        try {
            serialPort = new SerialPort(com);
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
        } catch (SerialPortException e) {
            logger.error("Problem to open serial port " + com, e);
        }

    }

    public String handleCommand(String command, Byte speed, Byte camNo) throws ViscaResponseReader.TimeoutException, SerialPortException {
        speed = speed == null ? 4 : speed;
        camNo = camNo == null ? 1 : camNo;
        command = command.toUpperCase();

        logger.info("speed " + speed);
        logger.info("cam number " + camNo);
        switch (command) {
            case "UP":
                logger.info("up");
                return sendPanTiltUp(speed, camNo);
            case "RIGHT":
                logger.info("right");
                return sendPanTiltRight(speed, camNo);
            case "LEFT":
                logger.info("left");
                return sendPanTiltLeft(speed, camNo);
            case "HOME":
                logger.info("home");
                return sendPanTiltHome();
            case "ZOOMTELE":
                logger.info("zoomtele");
                return sendZoomTeleStd(speed, camNo);
            case "ZOOMWIDE":
                logger.info("zoomwide");
                return sendZoomWideStd(speed, camNo);
            case "DOWN":
                logger.info("down");
                return sendPanTiltDown(speed, camNo);
        }

//        try {
//            byte[] response = ViscaResponseReader.readResponse(serialPort);
//            String responseString = byteArrayToString(response);
//            System.out.println("> " + responseString);
//            if (responseString.substring(3).startsWith("6")) {
//                return "command: " + command + " error";
//            } else {
//                response = ViscaResponseReader.readResponse(serialPort);
//                responseString = byteArrayToString(response);
//                return "command: " + command + " speed " + speed + ", response: " + responseString + " - ok";
//            }
//        } catch (ViscaResponseReader.TimeoutException var11) {
//            return "! TIMEOUT exception";
//        }
        return "";
    }

    private static void sleep(int timeSec) {
        try {
            Thread.sleep((long)(timeSec * 1000));
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }


    public String sendPanTiltHome() throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] cmdData = (new PanTiltHomeCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        logger.info("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
        return retrieveResponse();
    }

    public String sendPanTiltLeft(byte speed,byte camNo) throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] cmdData = (new PanTiltLeftCmd()).createCommandData();
        cmdData[3] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = camNo;
        cmdData = vCmd.getCommandData();
        logger.info("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
        return retrieveResponse();
    }

    public String sendPanTiltRight(byte speed,byte camNo) throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] cmdData = (new PanTiltRightCmd()).createCommandData();
        cmdData[3] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = camNo;
        cmdData = vCmd.getCommandData();
        logger.info("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
        return retrieveResponse();
    }

    public String sendPanTiltUp(byte speed,byte camNo) throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] cmdData = (new PanTiltUpCmd()).createCommandData();
        cmdData[4] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = camNo;
        cmdData = vCmd.getCommandData();
        logger.info("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
        return retrieveResponse();
    }

    public String sendPanTiltDown(byte speed,byte camNo) throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] cmdData = (new PanTiltDownCmd()).createCommandData();
        cmdData[4] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = camNo;
        cmdData = vCmd.getCommandData();
        logger.info("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
        return retrieveResponse();
    }


    private static void sendAddress(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new AddressCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 8;
        cmdData = vCmd.getCommandData();
        serialPort.writeBytes(cmdData);
    }


    private static String byteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        byte[] var5 = bytes;
        int var4 = bytes.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            sb.append(String.format("%02X ", b));
        }

        return sb.toString();
    }

    public String sendZoomTeleStd(byte speed,byte camNo) throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] cmdData = (new ZoomTeleStdCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        cmdData[3] = speed;
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = camNo;
        cmdData = vCmd.getCommandData();
        logger.info("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
        return retrieveResponse();
    }

    public String sendZoomWideStd(byte speed,byte camNo) throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] cmdData = (new ZoomWideStdCmd()).createCommandData();
        cmdData[3] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = camNo;
        cmdData = vCmd.getCommandData();
        logger.info("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
        return retrieveResponse();
    }

    private String retrieveResponse() throws SerialPortException, ViscaResponseReader.TimeoutException {
        byte[] response = ViscaResponseReader.readResponse(serialPort);
        String hex = byteArrayToString(response);
        logger.info("1st response " + hex);
        if (response[1] == 0x60){
            return "Error: " + hex;
        } else {
            response = ViscaResponseReader.readResponse(serialPort);
            hex = byteArrayToString(response);
            logger.info("2n response " + hex);
            return "Ok: " + hex;
        }
    }
}
