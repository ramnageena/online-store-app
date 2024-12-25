package com.online_store.errortrace;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
public class ErrorStack {

    public static void printCustomStackTrace(Throwable throwable) {
        // Capture the stack trace in a StringWriter
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        // Custom formatting of the stack trace
        String[] lines = sw.toString().split("\n");
        log.error("=== Custom Stack Trace Start ===");
        for (String line : lines) {
            log.error(">> {}", line.trim());
        }
        log.error("=== Custom Stack Trace End ===");
    }

    private ErrorStack(){

    }

}
