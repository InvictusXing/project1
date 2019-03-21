package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class StringAllocateSCG implements SimpleCodeGenerator {
	
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, 1);
		chunk.add(ASMOpcode.Add);
		
		chunk.add(ASMOpcode.Call, MemoryManager.MEM_MANAGER_ALLOCATE);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 12);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, 0);
		chunk.add(ASMOpcode.StoreC);
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
	
}
