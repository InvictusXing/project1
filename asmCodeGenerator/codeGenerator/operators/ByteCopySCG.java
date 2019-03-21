package asmCodeGenerator.codeGenerator.operators;

import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.*;
import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class ByteCopySCG implements SimpleCodeGenerator {

	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		Labeller labeller = new Labeller("byte-copy");
		String loopLabel  		= labeller.newLabel("loop");
		String joinLabel  		= labeller.newLabel("join");
	

		chunk.add(Label, loopLabel);
		chunk.add(PushD, RunTime.INDEX_TEMP_3);
		chunk.add(LoadI);
		chunk.add(JumpFalse, joinLabel);
					
			chunk.add(PushD, RunTime.INDEX_TEMP_1);
			chunk.add(LoadI);
			chunk.add(PushD, RunTime.INDEX_TEMP_2);
			chunk.add(LoadI);
			chunk.add(LoadC);
			chunk.add(StoreC);
			
			chunk.add(PushD, RunTime.INDEX_TEMP_3);
			chunk.add(PushD, RunTime.INDEX_TEMP_3);
			chunk.add(LoadI);
			chunk.add(PushI, 1);
			chunk.add(Subtract);
			chunk.add(StoreI);
			
			chunk.add(PushD, RunTime.INDEX_TEMP_1);
			chunk.add(PushD, RunTime.INDEX_TEMP_1);
			chunk.add(LoadI);
			chunk.add(PushI, 1);
			chunk.add(Add);
			chunk.add(StoreI);
			
			chunk.add(PushD, RunTime.INDEX_TEMP_2);
			chunk.add(PushD, RunTime.INDEX_TEMP_2);
			chunk.add(LoadI);
			chunk.add(PushI, 1);
			chunk.add(Add);
			chunk.add(StoreI);
			
		
		chunk.add(Jump, loopLabel);
		chunk.add(Label, joinLabel);
		
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {		
		return generate();
	}

}
