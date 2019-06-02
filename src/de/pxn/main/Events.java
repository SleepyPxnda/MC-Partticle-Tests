package de.pxn.main;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Events implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		p.getInventory().setItem(0, new ItemStack(Material.BLACK_BED, 1));
		p.getInventory().setItem(1, new ItemStack(Material.BEACON, 1));
	}

	BukkitTask heartcircle, staticheart;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void toogle(PlayerInteractEvent e) {
		final Player p = e.getPlayer();

		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.BLACK_BED) {

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

			else if (p.getItemInHand().getType() == Material.BEACON) {
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
			}

			else if (p.getItemInHand().getType() == Material.BUCKET) {
				new BukkitRunnable() {

					double t = Math.PI / 4;
					Location loc = p.getLocation();

					@Override
					public void run() {

						t += 0.1 * Math.PI;
						for (double theta = 0; theta <= Math.PI * 2; theta += Math.PI / 16) {
							double x = 1.5 * t * Math.cos(theta);
							double y = t * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
							double z = 1.5 * t * Math.sin(theta);

							loc.add(x, y, z);

							loc.getWorld().spawnParticle(Particle.BARRIER, loc, 1);

							loc.subtract(x, y, z);

						}

						if (t > 20) {
							this.cancel();
						}

					}

				}.runTaskTimer(Main.getInstance(), 0, 1);
				
			} else if (p.getItemInHand().getType() == Material.ARROW) {
				staticheart.cancel();
			}
		}

		else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.BLACK_BED) {
				heartcircle.cancel();
			}

		} else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand().getType() == Material.ARROW) {
				staticheart = new BukkitRunnable() {
					ArrayList<Location> points = new ArrayList<Location>();
					double t = 0;
					//double r = 1;
					//Location Blockloc = e.getClickedBlock().getLocation();

					@Override
					public void run() {
						Location Blockloc = e.getClickedBlock().getLocation();
						
						t += 0.03;

						double x =  8 * Math.pow(Math.sin(t), 3);
						double y = 0.5 * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t));
						double z = 0;

						Blockloc.add(x, y, z);
						
						
						points.add(Blockloc);

						for(Location temploc : points) {
							Blockloc.getWorld().spawnParticle(Particle.DRIP_LAVA, temploc, 1, 0, 0, 0);
						}
						//Blockloc.subtract(x, y, z);

						if (t > Math.PI * 2 ) {
							t = 0;
						}
					}

				}.runTaskTimer(Main.getInstance(), 0, 1);
			}
		}
	}
}
