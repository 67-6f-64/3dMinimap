package ss3dMinimap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="ss3dMinimap", name="さむい冬のミニマップ")
public class MiniMap
{
	boolean toggleMode = false;

	int drawPosX = 0;
	int drawPosY = 0;

	int rotX = 40;
	int rotY = 0;

	int entityDrawRange = 10;

	int chunkDrawRange = 4;

	double scale = 10.0D;

	boolean active = false;

	@Mod.Instance("ss3dMinimap")
	public static MiniMap インスタンス;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
        FMLCommonHandler.instance().bus().register(new RendererMinimap());
        FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
}
