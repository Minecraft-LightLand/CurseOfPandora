package dev.xkmc.curseofpandora.content.reality;

public class ClipMultiplierData extends CursePandoraUtil.Mult {

	private final double finVal;
	private final CursePandoraUtil.ValueConsumer last;

	private final double cap, min, bonus;

	ClipMultiplierData(double finVal, CursePandoraUtil.ValueConsumer last, double cap, double min, double bonus) {
		this.finVal = finVal;
		this.last = last;
		this.cap = cap;
		this.min = min;
		this.bonus = bonus;
	}

	@Override
	public double reverse() {
		double base = 1;
		double fv = finVal * last.get() / val;
		if (fv > this.cap) {
			val *= fv / this.cap;
		} else if (fv <= this.min) {
			base += this.bonus;
		}
		return base / val - 1;
	}

}
