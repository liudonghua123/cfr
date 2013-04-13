package org.benf.cfr.reader.bytecode.opcode;

import org.benf.cfr.reader.bytecode.analysis.opgraph.Op01WithProcessedDataAndByteJumps;
import org.benf.cfr.reader.bytecode.analysis.stack.StackDelta;
import org.benf.cfr.reader.bytecode.analysis.stack.StackDeltaImpl;
import org.benf.cfr.reader.bytecode.analysis.stack.StackSim;
import org.benf.cfr.reader.entities.ConstantPool;
import org.benf.cfr.reader.entities.ConstantPoolEntry;
import org.benf.cfr.reader.entities.Method;
import org.benf.cfr.reader.util.bytestream.ByteData;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 21/04/2011
 * Time: 08:10
 * To change this template use File | Settings | File Templates.
 */
public class OperationFactoryDup2X2 extends OperationFactoryDupBase {

    @Override
    public StackDelta getStackDelta(JVMInstr instr, byte[] data, ConstantPool cp, ConstantPoolEntry[] cpEntries,
                                    StackSim stackSim, Method method) {
        if (getCat(stackSim, 0) == 2) {
            if (getCat(stackSim, 1) == 2) {
                return new StackDeltaImpl(
                        getStackTypes(stackSim, 0, 1),
                        getStackTypes(stackSim, 0, 1, 0)
                );
            } else {
                checkCat(stackSim, 2, 1);
                return new StackDeltaImpl(
                        getStackTypes(stackSim, 0, 1, 2),
                        getStackTypes(stackSim, 0, 1, 2, 0)
                );
            }
        } else {
            if (getCat(stackSim, 2) == 2) {
                return new StackDeltaImpl(
                        getStackTypes(stackSim, 0, 1, 2),
                        getStackTypes(stackSim, 0, 1, 2, 0, 1)
                );
            } else {
                return new StackDeltaImpl(
                        getStackTypes(stackSim, 0, 1, 2, 3),
                        getStackTypes(stackSim, 0, 1, 2, 3, 0, 1)
                );
            }
        }
    }

    @Override
    public Op01WithProcessedDataAndByteJumps createOperation(JVMInstr instr, ByteData bd, ConstantPool cp, int offset) {
        byte[] args = null;
        int[] targetOffsets = null; // we know the nextr instr, it's our successor.
        return new Op01WithProcessedDataAndByteJumps(instr, args, targetOffsets, offset);
    }
}
