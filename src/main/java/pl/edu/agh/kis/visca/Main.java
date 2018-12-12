

package pl.edu.agh.kis.visca;

import jssc.SerialPort;
import jssc.SerialPortException;
import pl.edu.agh.kis.visca.cmd.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    private static Map<String, String> macros = new HashMap<String, String>();

    public static void main(String[] args) {
        String commName = args[0];
        SerialPort serialPort = new SerialPort(commName);

        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
            System.out.println("Address");
            sendAddress(serialPort);

            Scanner scanner = new Scanner(System.in);
           while (true) {
               System.out.println("Podaj komende lub makro");
               String command = scanner.nextLine();
               String macro = macros.get(command);


//               makro-nazwa komendy: komenda1, komenda2, komenda3
              if (command.startsWith("makro-")) {
                  String name = command.substring(command.indexOf("-")+1, command.indexOf(":"));
                  String macroDef = command.substring(command.indexOf(":") + 1).replaceAll("\\s+","");
                  macros.put(name, macroDef);
              } else if (macro != null) {
                  handleCommands(macro, serialPort);
              } else  {
                  command = command.replaceAll("\\s+","");
                  handleCommands(command, serialPort);

              }

           }

        } catch (SerialPortException var18) {
            System.out.println(var18);
        }
    }

    private static void handleCommands(String commands, SerialPort serialPort) throws SerialPortException {
        String[] commandArray = commands.split(",");
        for (String command : commandArray) {
            command = command.toUpperCase();
            if (command.startsWith("WAIT")) {
                System.out.println("wait");
                Integer waitSec = Integer.parseInt(command.substring("WAIT".length(), command.length() -1).trim());
                System.out.println(waitSec);
                sleep(waitSec);
            } else if (command.startsWith("UP")) {
                System.out.println("up");
                Byte speed = command.length() > 2 ? Byte.parseByte(command.substring("UP".length()).trim()) : 8;
                sendPanTiltUp(serialPort, speed);
                sleep(10);
            } else if (command.startsWith("RIGHT")) {
                System.out.println("right");
                Byte speed = command.length() > "right".length() ? Byte.parseByte(command.substring("RIGHT".length()).trim()) : 8;
                sendPanTiltRight(serialPort, speed);
                sleep(12);
            } else if ("RIGHT2".equals(command)) {
                System.out.println("right2");
                sendPanTiltRight2(serialPort);
                sleep(10);
            } else if (command.startsWith("LEFT")) {
                System.out.println("left");
                Byte speed = command.length() > "left".length() ? Byte.parseByte(command.substring("left".length()).trim()) : 8;
                sendPanTiltLeft(serialPort, speed);
                sleep(10);
            } else if ("LEFT2".equals(command)) {
                System.out.println("left2");
                sendPanTiltLeft2(serialPort);
                sleep(10);
            } else if ("HOME".equals(command)){
                System.out.println("home");
                sendPanTiltHome(serialPort);
                sleep(5);
            } else if ("ZOOMTELE".equals(command)){
                System.out.println("zoomtele");
                sendZoomTeleStd(serialPort);
                sleep(10);
            } else if ("ZOOMWIDE".equals(command)){
                System.out.println("zoomwide");
                sendZoomWideStd(serialPort);
                sleep(10);
            } else if ("DOWN".equals(command)) {
                System.out.println("down");
                Byte speed = command.length() > "down".length() ? Byte.parseByte(command.substring("down".length()).trim()) : 8;
                sendPanTiltDown(serialPort,speed);
                sleep(10);
            }

            try {
                byte[] response = ViscaResponseReader.readResponse(serialPort);
                System.out.println("> " + byteArrayToString(response));
                response = ViscaResponseReader.readResponse(serialPort);
                System.out.println("> " + byteArrayToString(response));
                String responseString = byteArrayToString(response);
                if (responseString.substring(3).startsWith("6")) {
                    System.out.println("error");
                } else {
                    System.out.println("ok");
                }
            } catch (ViscaResponseReader.TimeoutException var11) {
                System.out.println("! TIMEOUT exception");
            }
        }

    }

    private static void sleep(int timeSec) {
        try {
            Thread.sleep((long)(timeSec * 1000));
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }

    private static void sendClearAll(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new ClearAllCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 8;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltHome(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new PanTiltHomeCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltLeft(SerialPort serialPort,Byte speed) throws SerialPortException {
        byte[] cmdData = (new PanTiltLeftCmd()).createCommandData();
        cmdData[3] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltLeft2(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new PanTiltLeftCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 2;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltRight(SerialPort serialPort,Byte speed) throws SerialPortException {
        byte[] cmdData = (new PanTiltRightCmd()).createCommandData();
        cmdData[3] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltRight2(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new PanTiltRightCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 2;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltUp(SerialPort serialPort, Byte speed) throws SerialPortException {
        byte[] cmdData = (new PanTiltUpCmd()).createCommandData();
        cmdData[3] = speed;
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltDown(SerialPort serialPort,Byte speed) throws SerialPortException {
        byte[] cmdData = (new PanTiltDownCmd()).createCommandData();
        cmdData[3] = speed;

        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendPanTiltAbsolutePos(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new PanTiltAbsolutePosCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendAddress(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new AddressCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 8;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendGetPanTiltMaxSpeed(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new GetPanTiltMaxSpeedCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
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

    private static void sendZoomTeleStd(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new ZoomTeleStdCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }

    private static void sendZoomWideStd(SerialPort serialPort) throws SerialPortException {
        byte[] cmdData = (new ZoomWideStdCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 1;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
    }
}
