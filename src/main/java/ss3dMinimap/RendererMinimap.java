package ss3dMinimap;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class RendererMinimap
{
	static Minecraft mc = FMLClientHandler.instance().getClient();

    @SubscribeEvent
    public void render3dMinimap(TickEvent.RenderTickEvent event)
	{
		float ptt = event.renderTickTime;

		if(MiniMap.インスタンス.active)
		{
			GL11.glPushMatrix();
			GL11.glClear(0);

            GL11.glDisable(GL11.GL_CULL_FACE);//両面描画

			{//world
				GL11.glEnable(GL11.GL_BLEND);
				this.mc.entityRenderer.enableLightmap(0);
				this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

				WorldRenderer[] wrs = this.getSortedWorldRenderers();

				for(int c = 0;c<wrs.length;c++)
				{
					if(Math.abs((wrs[c].posXPlus-mc.thePlayer.posX)/16)<=MiniMap.インスタンス.chunkDrawRange&&
							Math.abs((wrs[c].posYPlus-mc.thePlayer.posY)/16)<=MiniMap.インスタンス.chunkDrawRange&&
							Math.abs((wrs[c].posZPlus-mc.thePlayer.posZ)/16)<=MiniMap.インスタンス.chunkDrawRange){

						GL11.glPushMatrix();
						wrs[c].isInFrustum = true;

						GL11.glTranslated(MiniMap.インスタンス.drawPosX+240,MiniMap.インスタンス.drawPosY+120,0);

						GL11.glScaled(MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*-0.1);

						GL11.glRotated(MiniMap.インスタンス.rotX-180, 1, 0, 0);
						GL11.glRotated(MiniMap.インスタンス.rotY, 0, 1, 0);

						GL11.glTranslated(wrs[c].posXMinus-mc.thePlayer.posX, wrs[c].posYMinus-mc.thePlayer.posY, wrs[c].posZMinus-mc.thePlayer.posZ);

						GL11.glCallList(wrs[c].getGLCallListForPass(0));
						GL11.glCallList(wrs[c].getGLCallListForPass(1));
						GL11.glPopMatrix();
					}
				}

				this.mc.entityRenderer.disableLightmap(0);
				GL11.glDisable(GL11.GL_BLEND);
			}

			{
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_TEXTURE_2D);

				GL11.glTranslated(MiniMap.インスタンス.drawPosX+240,MiniMap.インスタンス.drawPosY+120,0);

				GL11.glRotated(MiniMap.インスタンス.rotX-180, 1, 0, 0);
				GL11.glRotated(MiniMap.インスタンス.rotY, 0, 1, 0);

				Tessellator tessellator = Tessellator.instance;

				GL11.glLineWidth(5);
				GL11.glColor3d(0, 1, 0);
				tessellator.startDrawing(3);
				tessellator.addVertex(0,10,0);
				tessellator.addVertex(0,0,0);
				tessellator.draw();
				GL11.glColor3d(0, 0, 1);
				tessellator.startDrawing(3);
				tessellator.addVertex(0,0,10);
				tessellator.addVertex(0,0,0);
				tessellator.draw();
				GL11.glColor3d(1, 0, 0);
				tessellator.startDrawing(3);
				tessellator.addVertex(10,0,0);
				tessellator.addVertex(0,0,0);
				tessellator.draw();
				GL11.glLineWidth(1);

				GL11.glEnable(GL11.GL_TEXTURE_2D);

				GL11.glPopMatrix();
			}

			{//情報
				GL11.glPushMatrix();

				mc.fontRenderer.drawStringWithShadow(
						"拡大率"+MiniMap.インスタンス.scale+
						"rotX"+MiniMap.インスタンス.rotX+
						"rotY"+MiniMap.インスタンス.rotY+
						"描画チャンク(半径)"+MiniMap.インスタンス.chunkDrawRange+
						"描画エンティティ(半径)"+MiniMap.インスタンス.entityDrawRange, 0, 0, 0x00ff00);

				GL11.glPopMatrix();
			}

			{//TileEntity
				List<TileEntity> l = mc.renderGlobal.tileEntities;

				Iterator<TileEntity> it = l.iterator();
				while (it.hasNext()) {
					TileEntity e = it.next();

					if(e.getDistanceFrom(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ) < MiniMap.インスタンス.entityDrawRange*MiniMap.インスタンス.entityDrawRange*MiniMap.インスタンス.entityDrawRange)
					{
						GL11.glPushMatrix();

						GL11.glTranslated(MiniMap.インスタンス.drawPosX+240,MiniMap.インスタンス.drawPosY+120,0);

						GL11.glScaled(MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*-0.1);

						GL11.glRotated(MiniMap.インスタンス.rotX-180, 1, 0, 0);
						GL11.glRotated(MiniMap.インスタンス.rotY, 0, 1, 0);

                        TileEntityRendererDispatcher.instance.renderTileEntity(e, ptt);
						GL11.glPopMatrix();
					}
				}
			}

			{//Entity
				List<Entity> l = mc.theWorld.getLoadedEntityList();

				Iterator<Entity> it = l.iterator();
				while (it.hasNext()) {
					Entity e = it.next();

					if(e.getDistanceToEntity(mc.thePlayer) < MiniMap.インスタンス.entityDrawRange&&e != mc.thePlayer)
					{
						GL11.glPushMatrix();

						GL11.glTranslated(MiniMap.インスタンス.drawPosX+240,MiniMap.インスタンス.drawPosY+120,0);

						GL11.glScaled(MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*-0.1);

						GL11.glRotated(MiniMap.インスタンス.rotX-180, 1, 0, 0);
						GL11.glRotated(MiniMap.インスタンス.rotY, 0, 1, 0);

						RenderManager.instance.renderEntitySimple(e, ptt);
						GL11.glPopMatrix();
					}
				}

                {
                    GL11.glPushMatrix();

                    GL11.glTranslated(MiniMap.インスタンス.drawPosX+240,MiniMap.インスタンス.drawPosY+120,0);

                    GL11.glScaled(MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*0.1, MiniMap.インスタンス.scale*-0.1);

                    GL11.glRotated(MiniMap.インスタンス.rotX-180, 1, 0, 0);
                    GL11.glRotated(MiniMap.インスタンス.rotY, 0, 1, 0);

                    RenderManager.instance.renderEntitySimple(mc.thePlayer, ptt);
                    GL11.glPopMatrix();
                }
			}

            GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();
		}
	}

	private WorldRenderer[] getSortedWorldRenderers()
	{
		Class<RenderGlobal> c = RenderGlobal.class;
		Field f = null;
		try {
			f = c.getDeclaredField("field_72768_k");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			try {
				f = c.getDeclaredField("sortedWorldRenderers");
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			}
		}

		f.setAccessible(true);
		WorldRenderer[] n = null;
		try {
			n = (WorldRenderer[]) f.get(mc.renderGlobal);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return n;
	}
}
