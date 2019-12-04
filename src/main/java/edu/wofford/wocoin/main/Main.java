package edu.wofford.wocoin.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(ch.qos.logback.classic.Level.OFF);
        if (args.length >= 1) {
            String[] realArgs = Arrays.copyOfRange(args, 1, args.length);
            if (args[0].equals("0")) {
                Feature00Main.main(realArgs);
            }else if (args[0].equals("1")) {
                Feature01Main.main(realArgs);
            }else if (args[0].equals("2")) {
                Feature02Main.main(realArgs);
            }else if (args[0].equals("3")) {
                Feature03Main.main(realArgs);
            }else if (args[0].equals("4")) {
                Feature04Main.main(realArgs);
            }else if (args[0].equals("5")) {
                Feature05Main.main(realArgs);
            } else if (args[0].equals("6")) {
                Feature06Main.main(realArgs);
            } else if (args[0].equals("7")) {
                Feature07Main.main(realArgs);
            } else if (args[0].equals("8")) {
                Feature08Main.main(realArgs);
            } else if (args[0].equals("9")) {
                Feature09Main.main(realArgs);
            }else if (args[0].equals("10")) {
                Feature10Main.main(realArgs);
            } else {
                System.out.println("Feature " + args[0] + " is not valid.");
            }

        } else {
            System.out.println("You must specify a feature number.");
        }
    }
}