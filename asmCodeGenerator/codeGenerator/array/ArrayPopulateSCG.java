package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import semanticAnalyzer.types.*;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForPopulateSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForStoreSCG;

public class ArrayPopulateSCG implements SimpleCodeGenerator {
	ASMCodeChunk chunk = new ASMCodeChunk();
	
	@Override
	public ASMCodeChunk generate() {
		return null;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		assert var.length >= 1;
		Type type = (Type)var[0];
		
		if (type instanceof TypeLiteral) {
			type = ((TypeLiteral) type).getType();
		}

		Labeller labeller = new Labeller("populate-array");
		String startLabel = labeller.newLabel();
		String loopLabel  = labeller.newLabel("loop");
		String joinLabel  = labeller.newLabel("join");
		
		chunk.add(ASMOpcode.Label, startLabel);
		
		ArrayStackToTempSCG scg1 = new ArrayStackToTempSCG();
		chunk.append(scg1.generate());
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Label, loopLabel);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpFalse, joinLabel);

		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);

		OpcodeForPopulateSCG scg2 = new OpcodeForPopulateSCG(type);
		chunk.append(scg2.generate());
			
		OpcodeForStoreSCG scg3 = new OpcodeForStoreSCG(type);
		chunk.append(scg3.generate());
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode .StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Jump, loopLabel);
		chunk.add(ASMOpcode.Label, joinLabel);
	
		ArrayTempToStackSCG scg4 = new ArrayTempToStackSCG();
		chunk.append(scg4.generate());
			
		return chunk;
	}

	
}
