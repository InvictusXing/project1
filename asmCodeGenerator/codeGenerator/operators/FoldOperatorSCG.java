package asmCodeGenerator.codeGenerator.operators;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreFunctionSCG;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class FoldOperatorSCG {
	protected Type arrType;
	protected Type outType;
	protected ASMCodeFragment array;
	protected ASMCodeFragment lambda;
	
	public FoldOperatorSCG(Type arrT, Type outT) {
		this.arrType = arrT;		
		this.outType = outT;
		this.array = null;
		this.lambda = null;
	}
	public void setArray(ASMCodeFragment array) {
		this.array = array;
	}
	public void setLambda(ASMCodeFragment lambda) {
		this.lambda = lambda;
	}
	
	public ASMCodeFragment generate() {
		assert array != null;
		assert lambda != null;
		
		ASMCodeFragment code = new ASMCodeFragment(CodeType.GENERATES_VALUE);
			
		Labeller labeller = new Labeller("fold-operator");
		String startLabel 		= labeller.newLabel();
		String loopLabel  		= labeller.newLabel("loop");
		String joinLabel  		= labeller.newLabel("join");
		
		OpcodeForLoadSCG loadArrSCG = new OpcodeForLoadSCG(arrType);
		OpcodeForStoreFunctionSCG storeArrSCG = new OpcodeForStoreFunctionSCG(arrType);
		OpcodeForLoadSCG loadResultSCG = new OpcodeForLoadSCG(outType);

		code.add(Label, startLabel);
		
		code.add(PushD, RunTime.INDEX_TEMP_1);
		code.append(array);
		code.add(StoreI);
		
		code.add(PushD, RunTime.INDEX_TEMP_1);
		code.add(LoadI);
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI);
		code.add(JumpFalse, RunTime.FOLD_LENGTH_RUNTIME_ERROR);
		
		code.add(PushD, RunTime.INDEX_TEMP_1);
		code.add(LoadI);
		code.add(PushI, 16);
		code.add(Add);
		code.addChunk(loadArrSCG.generate());
		
		code.add(PushD, RunTime.INDEX_TEMP_2);
		code.add(PushI, 1);
		code.add(StoreI);
		
		code.add(Label, loopLabel);
		code.add(PushD, RunTime.INDEX_TEMP_1);
		code.add(LoadI);
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI);
		code.add(PushD, RunTime.INDEX_TEMP_2);
		code.add(LoadI);
		code.add(Subtract);
		code.add(JumpFalse, joinLabel);
				
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, arrType.getSize());
			code.add(Subtract);
			code.add(StoreI);
			
			code.addChunk(storeArrSCG.generate());
			
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, arrType.getSize());
			code.add(Subtract);
			code.add(StoreI);

			code.add(PushD, RunTime.INDEX_TEMP_1);
			code.add(LoadI);
			code.add(PushI, 16);
			code.add(Add);
			code.add(PushD, RunTime.INDEX_TEMP_2);
			code.add(LoadI);
			code.add(PushI, arrType.getSize());
			code.add(Multiply);
			code.add(Add);
			code.addChunk(loadArrSCG.generate());
			code.addChunk(storeArrSCG.generate());
			
			code.add(PushD, RunTime.INDEX_TEMP_1);
			code.add(LoadI);
			code.add(PushD, RunTime.INDEX_TEMP_2);
			code.add(LoadI);
			
			code.append(lambda);
			code.add(CallV);
			
			code.add(PushD, RunTime.INDEX_TEMP_2);
			code.add(Exchange);
			code.add(StoreI);
			code.add(PushD, RunTime.INDEX_TEMP_1);
			code.add(Exchange);
			code.add(StoreI);
			

			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.addChunk(loadResultSCG.generate());
			
			code.add(PushD, RunTime.STACK_POINTER, "%% restore stack pointer");
			code.add(PushD, RunTime.STACK_POINTER);
			code.add(LoadI);
			code.add(PushI, outType.getSize());
			code.add(Add);
			code.add(StoreI);
			
		code.add(PushD, RunTime.INDEX_TEMP_2);
		code.add(PushD, RunTime.INDEX_TEMP_2);
		code.add(LoadI);
		code.add(PushI, 1);
		code.add(Add);
		code.add(StoreI);
		
		code.add(Jump, loopLabel);
		code.add(Label, joinLabel);

		return code;
	}

}
