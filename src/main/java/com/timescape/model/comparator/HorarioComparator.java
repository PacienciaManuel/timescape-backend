package com.timescape.model.comparator;

import java.util.Comparator;

import com.timescape.model.HoraAtividade;

public class HorarioComparator implements Comparator<HoraAtividade> {

	@Override
	public int compare(HoraAtividade o1, HoraAtividade o2) {
		if (o1.getDiaSemana().ordinal() < o2.getDiaSemana().ordinal()) {
			return -1;
		}
		
		if (o1.getDiaSemana().ordinal() > o2.getDiaSemana().ordinal()) {
			return 1;
		}
		
		return 0;
	}
	
}
