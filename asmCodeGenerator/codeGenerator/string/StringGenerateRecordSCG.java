package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;

public class StringGenerateRecordSCG implements SimpleCodeGenerator {
	
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 6);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 4);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		return chunk;
	}
	
	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}
	
}
