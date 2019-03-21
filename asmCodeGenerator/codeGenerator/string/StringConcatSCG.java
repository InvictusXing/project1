package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class StringConcatSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		Labeller labeller = new Labeller("concat-string");
		String startLabel 		= labeller.newLabel();
		String allocateLabel 	= labeller.newLabel("allocate");
		String copyLabel1 		= labeller.newLabel("begin-string-copy-1");
		String copyLabel2 		= labeller.newLabel("begin-string-copy-2");
		
		chunk.add(ASMOpcode.Label, startLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Label, allocateLabel);
		StringAllocateSCG scg2 = new StringAllocateSCG();
		chunk.append(scg2.generate());
		
		StringGenerateRecordSCG scg3 = new StringGenerateRecordSCG();
		chunk.append(scg3.generate());
		
		StringCopySCG scg4 = new StringCopySCG();
		
		chunk.add(ASMOpcode.Label, copyLabel1);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_2);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushI, 0);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.PushI, 0);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_END);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushI, 0);
		chunk.add(ASMOpcode.StoreI);

		chunk.append(scg4.generate());
		
		chunk.add(ASMOpcode.Label, copyLabel2);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_2);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_OFFSET_1);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_END);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_START);
		chunk.add(ASMOpcode.PushI, 0);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_COPY_END);
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_2);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);

		chunk.append(scg4.generate());
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_ADDR_1);
		chunk.add(ASMOpcode.LoadI);
			
		return chunk;
	}

	@Override
	public ASMCodeChunk generate(Object... var) {
		return generate();
	}

}
