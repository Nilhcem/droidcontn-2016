package com.nilhcem.droidcontn.debug.stetho;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.io.PrintStream;
import java.util.List;

import javax.inject.Inject;

public class AppDumperPlugin implements DumperPlugin {

    @Inject
    public AppDumperPlugin() {
    }

    @Override
    public String getName() {
        return "droidcon";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        final PrintStream writer = dumpContext.getStdout();
        List<String> args = dumpContext.getArgsAsList();
        String commandName = (args.isEmpty()) ? "" : args.remove(0);

        switch (commandName) {
            case "hello":
                displayHello(writer);
                break;
            default:
                doUsage(writer);
                break;
        }
    }

    private void displayHello(PrintStream writer) {
        writer.println("Hello!");
    }

    private void doUsage(PrintStream writer) {
        writer.println("usage: dumpapp [arg]");
        writer.println();
        writer.println("arg:");
        writer.println("* hello: Display hello (polite plugin is polite)");
    }
}
