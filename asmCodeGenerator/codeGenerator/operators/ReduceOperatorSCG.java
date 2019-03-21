package asmCodeGenerator.codeGenerator.operators;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.array.ArrayAllocateSCG;
import asmCodeGenerator.codeGenerator.array.ArrayGenerateRecordSCG;
import asmCodeGenerator.codeGenerator.array.ArrayReleaseSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreFunctionSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreSCG;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class ReduceOperatorSCG {
	private Type inType;
	private Type outType;
	private ASMCodeFragment oldArray;
	private ASMCodeFragment lambda;
	
	public ReduceOperatorSCG(Type in, Type out) {
		this.inType = in;
		this.outType = out;
		this.oldArray = null;
		this.lambda = null;
	}
	public void setArray(ASMCodeFragment array) {
		this.oldArray = array;
	}
	public void setLambda(ASMCodeFragment lambda) {
		this.lambda = lambda;
	}
	
	public ASMCodeFragment generate() {
		assert oldArray != null;
		assert lambda != null;
		
		ASMCodeFragment code = new ASMCodeFragment(CodeType.GENERATES_VALUE);
			
		Labeller labeller = new Labeller("reduce-operator");
		String startLabel 		= labeller.newLabel();
		String allocateLabel 	= labeller.newLabel("allocate");
		String loopLabel  		= labeller.newLabel("loop");
		String falseLabel  		= labeller.newLabel("false-result");
		String joinLabel  		= labeller.newLabel("join");
		String cloneLabel  		= labeller.newLabel("clone");
		
		ArrayAllocateSCG allocateSCG = new ArrayAllocateSCG();
		ArrayReleaseSCG releaseSCG = new ArrayReleaseSCG();
		ArrayGenerateRecordSCG recordSCG = new ArrayGenerateRecordSCG(inType);
		
		OpcodeForLoadSCG loadArgSCG = new OpcodeForLoadSCG(inType);
		OpcodeForStoreFunctionSCG storeArgSCG = new OpcodeForStoreFunctionSCG(inType);
		OpcodeForStoreSCG storeGoodValueSCG = new OpcodeForStoreSCG(inType);
		
		OpcodeForLoadSCG loadResultSCG = new OpcodeForLoadSCG(outType);
		ByteCopySCG byteCopySCG = new ByteCopySCG();

		code.add(Label, startLabel);
		
		code.add(PushD, RunTime.ARRAY_TEMP_2);
		code.append(oldArray);
		code.add(StoreI);
		
		code.add(PushD, RunTime.ARRAY_TEMP_4);
		code.add(PushD, RunTime.ARRAY_TEMP_2);
		code.add(LoadI);
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI);
		code.add(StoreI);
		
		code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
		code.add(PushI, 0);
		code.add(StoreI);
		
		code.add(PushD, RunTime.ARRAY_TEMP_2);
		code.add(LoadI);
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI);
		code.add(Duplicate);
		code.add(PushI, inType.getSize());
		code.add(Multiply);
		
		code.add(Label, allocateLabel);
		code.addChunk(allocateSCG.generate());
		code.addChunk(recordSCG.generate());
		
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(PushI, 0);
		code.add(StoreI);
		
		code.add(Label, loopLabel);
		code.add(PushD, RunTime.ARRAY_TEMP_4);
		code.add(LoadI);
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(LoadI);
		code.add(Subtract);
		code.add(JumpFalse, joinLabel);
		
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, inType.getSize());
			code.add(Subtract);
			code.add(StoreI);
			
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(LoadI);
			code.add(PushI, 16);
			code.add(Add);
			code.add(PushD, RunTime.ARRAY_TEMP_3);
			code.add(LoadI);
			code.add(PushI, inType.getSize());
			code.add(Multiply);
			code.add(Add);
			code.addChunk(loadArgSCG.generate());
			code.addChunk(storeArgSCG.generate());
			
			code.add(PushD, RunTime.ARRAY_TEMP_1);
			code.add(LoadI);
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(LoadI);
			code.add(PushD, RunTime.ARRAY_TEMP_3);
			code.add(LoadI);
			code.add(PushD, RunTime.ARRAY_TEMP_4);
			code.add(LoadI);
			code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
			code.add(LoadI);
			
			code.append(lambda);
			code.add(CallV);
			
			code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.ARRAY_TEMP_4);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.ARRAY_TEMP_3);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.ARRAY_TEMP_1);
			code.add(Exchange);
			code.add(StoreI);
			
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.addChunk(loadResultSCG.generate());
			code.add(JumpFalse, falseLabel);
			
				code.add(PushD, RunTime.ARRAY_TEMP_1);
				code.add(LoadI);
				code.add(PushI, 16);
				code.add(Add);
				code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
				code.add(LoadI);
				code.add(PushI, inType.getSize());
				code.add(Multiply);
				code.add(Add);
				code.add(PushD, RunTime.ARRAY_TEMP_2);
				code.add(LoadI);
				code.add(PushI, 16);
				code.add(Add);
				code.add(PushD, RunTime.ARRAY_TEMP_3);
				code.add(LoadI);
				code.add(PushI, inType.getSize());
				code.add(Multiply);
				code.add(Add);
				code.addChunk(loadArgSCG.generate());
				code.addChunk(storeGoodValueSCG.generate());
				
				code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
				code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
				code.add(LoadI);
				code.add(PushI, 1);
				code.add(Add);
				code.add(StoreI);
				
			code.add(Label, falseLabel);
			
			code.add(PushD, RunTime.STACK_POINTER, "%% restore stack pointer");
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, outType.getSize());
			code.add(Add);
			code.add(StoreI);
			
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(PushD, RunTime.ARRAY_TEMP_3);
		code.add(LoadI);
		code.add(PushI, 1);
		code.add(Add);
		code.add(StoreI);
		
		code.add(Jump, loopLabel);
		code.add(Label, joinLabel);
		
			code.add(Label, cloneLabel);
		
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(PushD, RunTime.ARRAY_TEMP_1);
			code.add(LoadI);
			code.add(StoreI);
			
			code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
			code.add(LoadI);
			code.add(Duplicate);
			code.add(PushI, inType.getSize());
			code.add(Multiply);
		
			code.addChunk(allocateSCG.generate());
			code.addChunk(recordSCG.generate());
			
			code.add(PushD, RunTime.INDEX_TEMP_1);
			code.add(PushD, RunTime.ARRAY_TEMP_1);
			code.add(LoadI);
			code.add(PushI, 16);
			code.add(Add);
			code.add(StoreI);
			
			code.add(PushD, RunTime.INDEX_TEMP_2);
			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(LoadI);
			code.add(PushI, 16);
			code.add(Add);
			code.add(StoreI);
			
			code.add(PushD, RunTime.INDEX_TEMP_3);
			code.add(PushD, RunTime.REDUCE_ARR_LENGTH);
			code.add(LoadI);
			code.add(PushI, inType.getSize());
			code.add(Multiply);
			code.add(StoreI);
			
			code.addChunk(byteCopySCG.generate());

			code.add(PushD, RunTime.ARRAY_TEMP_2);
			code.add(LoadI);
			code.addChunk(releaseSCG.generate(inType));			

			
		code.add(PushD, RunTime.ARRAY_TEMP_1);
		code.add(LoadI);

		return code;
	}

}
