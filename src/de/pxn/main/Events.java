package de.pxn.main;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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

		p.getInventory().setItem(0, new ItemStack(Material.BED, 1));
		p.getInventory().setItem(1, new ItemStack(Material.BEACON, 1));
	}
	
	BukkitTask heartcircle;

	@EventHandler
	public void toogle(PlayerInteractEvent e) {
		final Player p = e.getPlayer();

		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.BED) {

				heartcircle = new BukkitRunnable() {

					
					double t = 0;
					double r = 1;

					public void run() {
						Location loc = p.getLocation();

						t = t + Math.PI / 8;

						double x = r * Math.cos(t);
						double y = loc.getY();
						double z = r * Math.sin(t);

						loc.add(x, 0, z);

						p.getWorld().playEffect(loc, Effect.HEART, 10);

						loc.subtract(x, 0, z);

						/*if (t > Math.PI * 4) {
							this.cancel();
						}*/
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
						
						loc.getWorld().playEffect(loc, Effect.NOTE, 1);
						
						/*for(Entity e : loc.getChunk().getEntities()) {
							if(e.getLocation().distance(loc) < 1.0) {
								e.setFireTicks(50);
							}
						} */
						
						/*if(loc.getWorld().getBlockAt(loc).getType() != Material.AIR) {
							loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
							//this.cancel();
						}*/
						
						loc.subtract(x,y,z);
										
						if (t > 30) {
							this.cancel();
						}
					}
				}.runTaskTimer(Main.getInstance(), 0, 1);
			}
			
			else if(p.getItemInHand().getType() == Material.BUCKET) {
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
		else if(e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(p.getItemInHand().getType() == Material.BED) {
				heartcircle.cancel();
			}
		}
	}
}
