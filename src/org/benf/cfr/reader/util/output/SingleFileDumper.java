package org.benf.cfr.reader.util.output;

import org.benf.cfr.reader.bytecode.analysis.types.JavaTypeInstance;
import org.benf.cfr.reader.entities.Method;
import org.benf.cfr.reader.state.TypeUsageInformation;
import org.benf.cfr.reader.util.getopt.Options;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;

public class SingleFileDumper extends StreamDumper {
    private final BufferedWriter writer;
    private final String encoding;

    SingleFileDumper(String filePath, String encoding, TypeUsageInformation typeUsageInformation, Options options, IllegalIdentifierDump illegalIdentifierDump, MovableDumperContext context) {
        super(typeUsageInformation, options, illegalIdentifierDump, context);
        this.encoding = encoding;
        BufferedWriter bw = null;
        try {
            if (encoding != null) {
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), encoding));
            } else {
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            }
        } catch (IOException e) {
            throw new CannotCreate(e);
        }
        this.writer = bw;
    }

    private SingleFileDumper(TypeUsageInformation typeUsageInformation, Options options, IllegalIdentifierDump illegalIdentifierDump, MovableDumperContext context, Set<JavaTypeInstance> emitted) {
        super(typeUsageInformation, options, illegalIdentifierDump, context, emitted);
        this.writer = null;
        this.encoding = null;
    }

    @Override
    protected void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new CannotCreate(e);
        }
    }

    @Override
    public void addSummaryError(Method method, String s) {
    }

    @Override
    public void close() {
        try {
            if (writer != null) {
                writer.flush();
            }
        } catch (IOException e) {
            throw new CannotCreate(e);
        }
    }

    public void forceClose() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new CannotCreate(e);
        }
    }

    @Override
    public Dumper withTypeUsageInformation(TypeUsageInformation innerclassTypeUsageInformation) {
        return this;  // Reuse same instance for all classes
    }

    @Override
    public BufferedOutputStream getAdditionalOutputStream(String description) {
        throw new CannotCreate("Additional output streams not supported in single file mode");
    }
}