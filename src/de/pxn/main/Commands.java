package de.pxn.main;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Commands implements CommandExecutor {

	BukkitTask heartcircle;
	Boolean heartRunning = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("pss")) {
				if (args.length < 1) {
					p.sendMessage("Usage: /pss [type]");
					p.sendMessage("Current Types: Beam,OneCircle,Wave");
				} else if (args.length == 1) {
					String type = args[0];

					if (type.equalsIgnoreCase("Beam")) {
						new BukkitRunnable() {

							double t = 0;
							Location loc = p.getEyeLocation();
							Vector direction = loc.getDirection().normalize();

							public void run() {
								t += 0.5;

								double x = direction.getX() * t;
								double y = direction.getY() * t;
								double z = direction.getZ() * t;

								loc.add(x, y, z);

								loc.getWorld().playEffect(loc, Effect.DRAGON_BREATH, 1);

								/*
								 * for(Entity e : loc.getChunk().getEntities()) {
								 * if(e.getLocation().distance(loc) < 1.0) { e.setFireTicks(50); } }
								 */

								/*
								 * if(loc.getWorld().getBlockAt(loc).getType() != Material.AIR) {
								 * loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT); //this.cancel(); }
								 */

								loc.subtract(x, y, z);

								if (t > 30) {
									this.cancel();
								}
							}
						}.runTaskTimer(Main.getInstance(), 0, 1);
					} else if (type.equalsIgnoreCase("OneCircle")) {

						if (heartRunning == true) {
							heartRunning = false;
							heartcircle.cancel();
						} else if(heartRunning == false) {
							heartRunning = true;
							heartcircle = new BukkitRunnable() {

								double t = 0;
								double r = 1;

								public void run() {
									Location loc = p.getLocation();

									t += Math.PI / 8;

									double x = r * Math.cos(t);
									double z = r * Math.sin(t);
									double y = (z / r) + 1;

									loc.add(x, y, z);

									p.getWorld().spawnParticle(Particle.WATER_BUBBLE, loc, 1);

									loc.subtract(x, y, z);

									/*
									 * if (t > Math.PI * 4) { this.cancel(); }
									 */
								}

							}.runTaskTimer(Main.getInstance(), 0, 1);
						}
					}
					else if(type.equalsIgnoreCase("Wave")) {
						new BukkitRunnable() {

							double t = Math.PI/4;
							Location loc = p.getLocation();
			
							@Override
							public void run() {
								
								t += 0.1 * Math.PI;
								for(double theta = 0; theta <= Math.PI * 2; theta += Math.PI/16) {
									double x = 1.5 *t * Math.cos(theta);
									double y = t * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
									double z = 1.5 * t* Math.sin(theta);
									
									loc.add(x, y, z);
									
									loc.getWorld().playEffect(loc, Effect.SMOKE, 1);
									
									loc.subtract(x,y,z);
									
						
								}
								
								if(t > 20) {
									this.cancel();
								}
								
								
							}
							
						}.runTaskTimer(Main.getInstance(), 0, 1);
					}
				}

			}
		}

		return false;
	}

}
