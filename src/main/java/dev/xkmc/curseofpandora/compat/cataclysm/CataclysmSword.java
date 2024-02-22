package dev.xkmc.curseofpandora.compat.cataclysm;

import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import com.mojang.datafixers.util.Pair;
import dev.xkmc.curseofpandora.content.weapon.EmptyClickListener;
import dev.xkmc.curseofpandora.content.weapon.WeaponTier;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;

import java.util.function.IntConsumer;

public class CataclysmSword extends SwordItem implements EmptyClickListener {

	public CataclysmSword(Properties props) {
		super(WeaponTier.ANGELIC_JUDGEMENT, //TODO
				10, -2.4f, props);
	}

	@Override
	public void clickEmpty(ItemStack stack, Player player) {
		float strength = player.getAttackStrengthScale(0.5f);
		if (strength < 0.9f)
			return;
		player.resetAttackStrengthTicker();
		if (player.level().isClientSide()) return;
		int dist = 40;
		float xRot = player.getXRot();
		float yRot = player.getYRot();
		Vec3 start = player.getEyePosition();
		var dir = RayTraceUtil.getRayTerm(Vec3.ZERO, xRot, yRot, 1);
		var ax1 = getRect(dir).getSecond();
		double theta = Math.PI / 4;
		addLaserBeams(player, start, ax1.scale(Math.sin(theta)).add(dir.scale(Math.cos(theta))), dist);
		addLaserBeams(player, start, ax1.scale(Math.sin(-theta)).add(dir.scale(Math.cos(-theta))), dist);
		addFireballs(player, start, dir, ax1, Math.PI / 8);
		addFireballs(player, start, dir, ax1, 0);
		addFireballs(player, start, dir, ax1, -Math.PI / 8);
	}

	private static void addFireballs(LivingEntity player, Vec3 start, Vec3 dir, Vec3 ax1, double theta) {
		var ori = ax1.scale(Math.sin(theta)).add(dir.scale(Math.cos(theta)));
		for (int i = 0; i < 10; i++) {
			shootFireball(player, start, ori, i, i == 0, i % 2 == 0);
		}
	}

	private static void shootFireball(LivingEntity user, Vec3 start, Vec3 dir, int order, boolean abyss, boolean soul) {
		Projectile shot;
		int delay = 10000;
		IntConsumer setup;
		if (abyss) {
			var bullet = new Ignis_Abyss_Fireball_Entity(user.level(), user);
			bullet.setUp(delay);
			shot = bullet;
			setup = bullet::setUp;
		} else {
			var bullet = new Ignis_Fireball_Entity(user.level(), user);
			bullet.setUp(delay);
			bullet.setSoul(soul);
			shot = bullet;
			setup = bullet::setUp;
		}
		Vec3 pos = start.add(dir.scale(3 - 0.2 * order));
		shot.setPos(pos);
		user.level().addFreshEntity(shot);

		GeneralEventHandler.schedulePersistent(new FireballShooter(3, 4 * (order + 1), () -> {
			setup.accept(-60);
			shot.shoot(dir.x, dir.y, dir.z, 3F - 0.2f * order, order * 0.7f);
		})::tick);
	}

	private Pair<Vec3, Vec3> getRect(Vec3 dir) {
		double val = (dir.x * dir.x + dir.z * dir.z);
		Vec3 ax0 = val < 1e-6 ? new Vec3(1, 0, 0) :
				new Vec3(-dir.x * dir.y, val, -dir.z * dir.y).normalize();
		Vec3 ax1 = dir.cross(ax0).normalize();
		return Pair.of(ax0, ax1);
	}

	private void addLaserBeams(Player player, Vec3 start, Vec3 ori, int dist) {
		Vec3 end = start.add(ori.scale(dist));
		Vec3 clip = player.level().clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player)).getLocation();
		var path = clip.subtract(start);
		int count = (int) path.length() - 1;
		if (count <= 0) return;
		var dir = path.normalize();
		var pair = getRect(dir);
		GeneralEventHandler.schedulePersistent(
				new LaserAdder(start, dir, pair.getFirst(), pair.getSecond(),
						player, count, 1, 60)::tick);

	}

	private static class LaserAdder {

		private final Vec3 start, dir, ax0, ax1;
		private final LivingEntity player;
		private final int count, space, dur;
		private final boolean shift;

		private int timer = 0;

		private LaserAdder(Vec3 start, Vec3 dir, Vec3 ax0, Vec3 ax1, LivingEntity player, int count, int space, int dur) {
			this.start = start;
			this.dir = dir;
			this.ax0 = ax0;
			this.ax1 = ax1;
			this.player = player;
			this.count = count;
			this.space = space;
			this.dur = dur;
			this.shift = player.isShiftKeyDown();
		}

		private boolean tick() {
			if (!player.isAlive()) return true;
			timer++;
			if (timer % space == 0) {
				addLaser(timer / space);
			}
			return timer >= count * space;
		}

		private void addLaser(int i) {
			Vec3 pos = start.add(dir.scale(i));
			double theta = shift ? i % 2 * Math.PI : player.getRandom().nextDouble() * Math.PI * 2;
			Vec3 ori = ax0.scale(Math.sin(theta)).add(ax1.scale(Math.cos(theta)));
			var xr = -Math.asin(ori.y);
			var yr = -Math.atan2(ori.x, ori.z);
			FreeLaserBeam beam = new FreeLaserBeam(CataclysmRegistry.LASER_BEAM.get(),
					player.level(), player, pos, ori, (float) (yr + Math.PI / 2), (float) -xr, dur);
			player.level().addFreshEntity(beam);
			if (!shift) {
				GeneralEventHandler.schedulePersistent(new RotationHandler(beam, ax0, ax1, theta,
						Math.PI / dur, dur + 20)::tick);
			}
		}

	}

	private static class RotationHandler {

		private final FreeLaserBeam beam;
		private final Vec3 ax0, ax1;
		private final double theta, delta;
		private final int duration;
		private int timer;

		public RotationHandler(FreeLaserBeam beam, Vec3 ax0, Vec3 ax1, double theta, double delta, int duration) {
			this.beam = beam;
			this.ax0 = ax0;
			this.ax1 = ax1;
			this.theta = theta;
			this.delta = delta;
			this.duration = duration;
		}

		private boolean tick() {
			if (beam.isRemoved()) return true;
			timer++;
			double theta = this.theta + delta * timer;
			Vec3 ori = ax0.scale(Math.sin(theta)).add(ax1.scale(Math.cos(theta)));
			var xr = -Math.asin(ori.y);
			var yr = -Math.atan2(ori.x, ori.z);
			beam.setYaw((float) (yr + Math.PI / 2));
			beam.setPitch((float) -xr);
			return timer >= duration;
		}

	}

	private static class FireballShooter {

		private final int duration;
		private final Runnable task;
		private int count, timer;

		private FireballShooter(int count, int duration, Runnable task) {
			this.count = count;
			this.duration = duration;
			this.task = task;
		}

		private boolean tick() {
			timer++;
			if (timer >= duration) {
				timer = 0;
				count--;
				task.run();
			}
			return count == 0;
		}

	}

}
