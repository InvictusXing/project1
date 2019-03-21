package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import lexicalAnalyzer.Keyword;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForCloneSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;

public class ArrayReverseSCG implements SimpleCodeGenerator {
	Type subtype;
	
	public ArrayReverseSCG(Type t) {
		this.subtype = t;
	}
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
			
		Labeller labeller = new Labeller("reverse-array");
		String startLabel 		= labeller.newLabel();
		String allocateLabel 	= labeller.newLabel("allocate");
		String recordLabel  	= labeller.newLabel("create-record");
		String copyLabel  		= labeller.newLabel("copy");
		String loopLabel  		= labeller.newLabel("loop");
		String joinLabel  		= labeller.newLabel("join");
		
		ArrayAllocateSCG allocateSCG = new ArrayAllocateSCG();
		OpcodeForLoadSCG loadSCG = new OpcodeForLoadSCG(subtype);
		OpcodeForCloneSCG storeSCG = new OpcodeForCloneSCG(subtype);
	
		chunk.add(ASMOpcode.Label, startLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);

		chunk.add(ASMOpcode.Label, allocateLabel);
		chunk.append(allocateSCG.generate(Keyword.CLONE));
		
		chunk.add(ASMOpcode.Label, recordLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 7);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Label, copyLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);		
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, -1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.Multiply);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Label, loopLabel);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpFalse, joinLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
		chunk.add(ASMOpcode.LoadI);
		chunk.append(loadSCG.generate());
		chunk.append(storeSCG.generate());
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.StoreI);
			
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_6);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);		
		chunk.add(ASMOpcode.Jump, loopLabel);
		chunk.add(ASMOpcode.Label, joinLabel);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
