package asmCodeGenerator.codeGenerator.string;

import asmCodeGenerator.Labeller;
import asmCodeGenerator.codeGenerator.SimpleCodeGenerator;
import asmCodeGenerator.codeStorage.*;
import asmCodeGenerator.runtime.RunTime;

public class StringReverseSCG implements SimpleCodeGenerator {
	
	@Override
	public ASMCodeChunk generate() {
		ASMCodeChunk chunk = new ASMCodeChunk();
		
		Labeller labeller = new Labeller("reverse-string");
		String startLabel 		= labeller.newLabel();
		String allocateLabel 	= labeller.newLabel("allocate");
		String copyLabel1 		= labeller.newLabel("begin-string-copy");
		
		chunk.add(ASMOpcode.Label, startLabel);
		
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.Exchange);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.PushD, RunTime.STRING_SIZE_1);
		chunk.add(ASMOpcode.PushD, RunTime.INDEX_TEMP_1);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.PushI, 8);
		chunk.add(ASMOpcode.Add);
		chunk.add(ASMOpcode.LoadI);
		chunk.add(ASMOpcode.StoreI);
		
		chunk.add(ASMOpcode.Label, allocateLabel);
		StringAllocateSCG scg2 = new StringAllocateSCG();
		chunk.append(scg2.generate());
		
		StringGenerateRecordSCG scg3 = new StringGenerateRecordSCG();
		chunk.append(scg3.generate());
		
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

		StringReverseCopySCG scg4 = new StringReverseCopySCG();
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
