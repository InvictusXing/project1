package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import parseTree.nodeTypes.*;
import semanticAnalyzer.types.*;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForCloneSCG;
import asmCodeGenerator.codeGenerator.opcodeManipulation.OpcodeForLoadSCG;

public class ArrayCloneSCG implements SimpleCodeGenerator {
	ASMCodeChunk chunk = new ASMCodeChunk();

	@Override
	public ASMCodeChunk generate() {
		return null;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		assert var.length >= 1;
		if (var[0] instanceof ArrayNode) {
			ArrayNode node = (ArrayNode)var[0];
			Type type = node.getType();
			Type subtype = ((ArrayType) type).getSubtype();
				
			Labeller labeller = new Labeller("clone-array");
			String startLabel  		= labeller.newLabel();
			String recordLabel  	= labeller.newLabel("record");
			String dataLabel  		= labeller.newLabel("data");
			String loopLabel  		= labeller.newLabel("loop");
			String joinLabel  		= labeller.newLabel("join");
			String loopCopyLabel  	= labeller.newLabel("loop-copy");
			String joinCopyLabel  	= labeller.newLabel("join-copy");
		
			chunk.add(ASMOpcode.Label, startLabel);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.PushI, 16);
			chunk.add(ASMOpcode.StoreI);
				
			chunk.add(ASMOpcode.Label, recordLabel);
			chunk.add(ASMOpcode.Label, loopLabel);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.JumpFalse, joinLabel);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 4);
			chunk.add(ASMOpcode.Subtract);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.LoadI);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.Exchange);
			chunk.add(ASMOpcode.StoreI);
				
			chunk.add(ASMOpcode.Jump, loopLabel);
			chunk.add(ASMOpcode.Label, joinLabel);
			
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
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Multiply);
			chunk.add(ASMOpcode.PushI, 16);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.Label, dataLabel);
			chunk.add(ASMOpcode.Label, loopCopyLabel);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.JumpFalse, joinCopyLabel);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_3);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushI, 1);
			chunk.add(ASMOpcode.Subtract);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_4);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Subtract);
			chunk.add(ASMOpcode.StoreI);
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_2);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			OpcodeForLoadSCG scg1 = new OpcodeForLoadSCG(subtype);
			chunk.append(scg1.generate());
			
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_1);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.PushD, RunTime.ARRAY_TEMP_5);
			chunk.add(ASMOpcode.LoadI);
			chunk.add(ASMOpcode.Add);
			chunk.add(ASMOpcode.Exchange);
			OpcodeForCloneSCG scg2 = new OpcodeForCloneSCG(subtype);
			chunk.append(scg2.generate());
				
			chunk.add(ASMOpcode.Jump, loopCopyLabel);
			chunk.add(ASMOpcode.Label, joinCopyLabel);
			
			return chunk;
		}
		
		return null;
	}

}
