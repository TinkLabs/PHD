package com.tinklabs.phd.util;

import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Created by root on 4/28/16.
 */
public class RuntimeUtils {
    /**
     * Sun property pointing the main class and its arguments.
     * Might not be defined on non Hotspot VM implementations.
     */
    public static final String SUN_JAVA_COMMAND = "sun.java.command";

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    public static void restartApplication(Runnable runBeforeRestart) throws IOException {
        try {
// java binary
            String java = System.getProperty("java.home");
            if (java.endsWith("/jre"))
                java = java.substring(0, java.indexOf("/jre"));
            java = java + "/bin/java";
// vm arguments
            List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            StringBuffer vmArgsOneLine = new StringBuffer();
            for (String arg : vmArguments) {
// if it's the agent argument : we ignore it otherwise the
// address of the old application and the new one will be in conflict
                if (!arg.contains("-agentlib")) {
                    vmArgsOneLine.append(arg);
                    vmArgsOneLine.append(" ");
                }
            }
// init the command to execute, add the vm args
            final StringBuffer cmd = new StringBuffer("" + java + " " + vmArgsOneLine);

// program main and program arguments
            String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
// program main is a jar
            if (mainCommand[0].endsWith(".jar")) {
// if it's a jar, add -jar mainJar
                cmd.append("-jar " + new File(mainCommand[0]).getPath());
            } else {
// else it's a .class, add the classpath and mainClass
                cmd.append("-classpath " + System.getProperty("java.class.path") + " " + mainCommand[0]);
            }

// finally add program arguments
            for (int i = 1; i < mainCommand.length; i++) {
                cmd.append(" ");
                cmd.append(mainCommand[i]);
            }

            System.out.println(cmd);
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec(cmd.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

// exit
            Platform.exit();
        } catch (Exception e) {
// something went wrong
            throw new IOException("Error while trying to restart the application", e);
        }
    }

}
