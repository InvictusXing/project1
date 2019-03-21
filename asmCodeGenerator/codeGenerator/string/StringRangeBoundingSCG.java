package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class StringRangeBoundingSCG implements SimpleCodeGenerator {
	
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract);
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);	
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_3);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Subtract); 
		chunk.add(ASMOpcode.JumpNeg, RunTime.BAD_INDEX_RUNTIME_ERROR);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
