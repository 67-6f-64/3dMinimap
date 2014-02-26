package ss3dMinimap;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;

public class KeyHandler
{
    private KeyBinding key_UP  = new KeyBinding("minimap UP", Keyboard.KEY_UP, "ss3dMinimap");
    private KeyBinding key_DOWN  = new KeyBinding("minimap DOWN", Keyboard.KEY_DOWN, "ss3dMinimap");
    private KeyBinding key_LEFT  = new KeyBinding("minimap LEFT", Keyboard.KEY_LEFT, "ss3dMinimap");
    private KeyBinding key_RIGHT  = new KeyBinding("minimap RIGHT", Keyboard.KEY_RIGHT, "ss3dMinimap");
    private KeyBinding key_enable  = new KeyBinding("minimap enable", Keyboard.KEY_HOME, "ss3dMinimap");
    private KeyBinding key_zoomin  = new KeyBinding("minimap zoomin", Keyboard.KEY_INSERT, "ss3dMinimap");
    private KeyBinding key_zoomout  = new KeyBinding("minimap zoomout", Keyboard.KEY_DELETE, "ss3dMinimap");
    private KeyBinding key_toggle  = new KeyBinding("minimap toggle", Keyboard.KEY_END, "ss3dMinimap");
    private KeyBinding key_toggleEnable  = new KeyBinding("minimap toggle enable", Keyboard.KEY_LCONTROL, "ss3dMinimap");
    private KeyBinding key_reset  = new KeyBinding("minimap reset", Keyboard.KEY_PAUSE, "ss3dMinimap");
    private KeyBinding key_headUP  = new KeyBinding("minimap UP", Keyboard.KEY_NUMPAD8, "ss3dMinimap");
    private KeyBinding key_headDOWN  = new KeyBinding("minimap DOWN", Keyboard.KEY_NUMPAD2, "ss3dMinimap");
    private KeyBinding key_headLEFT  = new KeyBinding("minimap LEFT", Keyboard.KEY_NUMPAD4, "ss3dMinimap");
    private KeyBinding key_headRIGHT  = new KeyBinding("minimap RIGHT", Keyboard.KEY_NUMPAD6, "ss3dMinimap");

	Minecraft mc = FMLClientHandler.instance().getClient();

    public KeyHandler()
    {
        ClientRegistry.registerKeyBinding(key_UP);
        ClientRegistry.registerKeyBinding(key_DOWN);
        ClientRegistry.registerKeyBinding(key_RIGHT);
        ClientRegistry.registerKeyBinding(key_enable);
        ClientRegistry.registerKeyBinding(key_zoomin);
        ClientRegistry.registerKeyBinding(key_zoomout);
        ClientRegistry.registerKeyBinding(key_toggle);
        ClientRegistry.registerKeyBinding(key_toggleEnable);
        ClientRegistry.registerKeyBinding(key_reset);
        ClientRegistry.registerKeyBinding(key_headUP);
        ClientRegistry.registerKeyBinding(key_headDOWN);
        ClientRegistry.registerKeyBinding(key_headLEFT);
        ClientRegistry.registerKeyBinding(key_headRIGHT);
    }

    @SubscribeEvent
    public void Key3dMinimapFromEvent(InputEvent.KeyInputEvent event){
		if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
		{
			if(key_UP.isPressed()&&MiniMap.インスタンス.entityDrawRange < 100)
			{
				MiniMap.インスタンス.entityDrawRange += 10;
			}
			else if(key_DOWN.isPressed()&&MiniMap.インスタンス.entityDrawRange > 10)
            {
				MiniMap.インスタンス.entityDrawRange -= 10;
			}

            if(key_LEFT.isPressed()&&MiniMap.インスタンス.chunkDrawRange < 16)
            {
				MiniMap.インスタンス.chunkDrawRange += 1;
			}
			else if(key_RIGHT.isPressed()&&MiniMap.インスタンス.chunkDrawRange > 1)
            {
				MiniMap.インスタンス.chunkDrawRange -= 1;
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        {
			if(key_UP.isPressed()&&MiniMap.インスタンス.drawPosY < 1000)
			{
				MiniMap.インスタンス.drawPosY += 2;
			}
			else if(key_DOWN.isPressed()&&MiniMap.インスタンス.drawPosY > -1000)
			{
				MiniMap.インスタンス.drawPosY -= 2;
			}

			if(key_LEFT.isPressed()&&MiniMap.インスタンス.drawPosX < 1000)
			{
				MiniMap.インスタンス.drawPosX += 2;
			}
			else if(key_RIGHT.isPressed()&&MiniMap.インスタンス.drawPosX > -1000)
			{
				MiniMap.インスタンス.drawPosX -= 2;
			}
		}
		else
		{
			if(key_UP.isPressed()&&MiniMap.インスタンス.rotX < 90)
			{
				MiniMap.インスタンス.rotX += 2;
			}
			else if(key_DOWN.isPressed()&&MiniMap.インスタンス.rotX > -90)
			{
				MiniMap.インスタンス.rotX -= 2;
			}

			if(key_LEFT.isPressed())
			{
				MiniMap.インスタンス.rotY += 2;
			}
			else if(key_RIGHT.isPressed())
			{
				MiniMap.インスタンス.rotY -= 2;
			}
		}

		if(key_zoomin.isPressed()&&MiniMap.インスタンス.scale < 200)
		{
			MiniMap.インスタンス.scale += 1;
		}
		else if(key_zoomout.isPressed()&&MiniMap.インスタンス.scale > 0)
		{
			MiniMap.インスタンス.scale -= 1;
		}

		if(MiniMap.インスタンス.toggleMode){
			if(key_toggleEnable.isPressed())
			{
				MiniMap.インスタンス.active = true;
			}
			else MiniMap.インスタンス.active = false;
		}
		else
		{
			if(key_enable.isPressed())
			{
                MiniMap.インスタンス.active = !MiniMap.インスタンス.active;
			}
		}

		if(key_toggleEnable.isPressed())
		{
            MiniMap.インスタンス.toggleMode = !MiniMap.インスタンス.toggleMode;
		}

		if(key_reset.isPressed())
		{
			MiniMap.インスタンス.drawPosX = 0;
			MiniMap.インスタンス.drawPosY = 0;

			MiniMap.インスタンス.rotX = 40;
			MiniMap.インスタンス.rotY = 0;

			MiniMap.インスタンス.entityDrawRange = 10;

			MiniMap.インスタンス.chunkDrawRange = 4;

			MiniMap.インスタンス.scale = 10.0D;
		}

		if(Math.abs(MiniMap.インスタンス.rotY) > 360)
			MiniMap.インスタンス.rotY = 0;

		double rotr = 1;

		if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
		{
			rotr = 0.1F;
		}

		if(key_headLEFT.isPressed())
		{
			mc.thePlayer.rotationYaw -= rotr;
		}
		else if(key_headRIGHT.isPressed())
		{
			mc.thePlayer.rotationYaw += rotr;
		}

		if(key_headUP.isPressed())
		{
			mc.thePlayer.rotationPitch -= rotr;
		}
		else if(key_headDOWN.isPressed())
		{
			mc.thePlayer.rotationPitch += rotr;
		}

		if(mc.currentScreen != null)
		{
			MiniMap.インスタンス.active = false;
		}

	}
}
