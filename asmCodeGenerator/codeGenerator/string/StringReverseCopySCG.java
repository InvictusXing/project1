package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;

public class StringReverseCopySCG implements SimpleCodeGenerator {
	
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		Labeller labeller = new Labeller("copy-string");
		String startLabel  		= labeller.newLabel();
		String loopCopyLabel  	= labeller.newLabel("loop-copy");
		String joinCopyLabel  	= labeller.newLabel("join-copy");
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, -1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);		
		
		chunk.add(ASMOpcode.Label, startLabel);
		chunk.add(ASMOpcode.Label, loopCopyLabel);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_END);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.JumpFalse, joinCopyLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.LoadC);
		chunk.add(ASMOpcode.StoreC);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, -1);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
			
		chunk.add(ASMOpcode.Jump, loopCopyLabel);
		chunk.add(ASMOpcode.Label, joinCopyLabel);
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
