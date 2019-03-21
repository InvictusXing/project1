package asmCodeGenerator.codeGenerator.array;

import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class ArrayOffsetSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		ArrayIndexBoundingSCG scg = new ArrayIndexBoundingSCG();
		chunk.append(scg.generate());
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Multiply);
		chunk.add(ASMOpcode.PushI, 16);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.Add);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
