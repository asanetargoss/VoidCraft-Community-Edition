package Tamaized.Voidcraft.GUI.client;

import java.io.DataOutputStream;
import java.io.IOException;

import Tamaized.Voidcraft.voidCraft;
import Tamaized.Voidcraft.capabilities.CapabilityList;
import Tamaized.Voidcraft.capabilities.vadeMecum.IVadeMecumCapability;
import Tamaized.Voidcraft.handlers.VadeMecumPacketHandler;
import Tamaized.Voidcraft.network.ServerPacketHandler;
import Tamaized.Voidcraft.proxy.ClientProxy;
import Tamaized.Voidcraft.vadeMecum.VadeMecumEntry;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VadeMecumGUI extends GuiScreen {

	public static final ResourceLocation TEXTURES = new ResourceLocation(voidCraft.modid, "textures/gui/VadeMecum/VadeMecum.png");

	private int vadeW;
	private int vadeH;
	private int vadeX;
	private int vadeY;

	private final EntityPlayer player;
	private IVadeMecumCapability playerStats;
	private VadeMecumEntry entry;
	private VadeMecumEntry nextEntry;
	private int pageNumber = 0;
	private ItemStack renderStackHover;

	private VadeMecumGUI.ArrowButton button_back;
	private VadeMecumGUI.ArrowButton button_forward;
	private VadeMecumGUI.OverlayButton button_entryBack;
	private VadeMecumGUI.OverlayButton button_credits;

	private static enum Button {
		NULL, Back, Forward, EntryBack, Credits
	}

	private static int getButtonID(Button b) {
		return b.ordinal();
	}

	private static Button getButtonFromID(int id) {
		return id > Button.values().length ? Button.NULL : Button.values()[id];
	}

	private boolean displayIntro = true;

	public VadeMecumGUI(EntityPlayer p) {
		player = p;
		initPosSize();
	}

	private void initPosSize() {
		vadeW = 256 + 140;
		vadeH = 192 + 25;
		vadeX = (width - vadeW) / 2;
		vadeY = 5;
	}

	public int getX() {
		return vadeX;
	}

	public int getY() {
		return vadeY;
	}

	public int getW() {
		return vadeW;
	}

	public int getH() {
		return vadeH;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		initPosSize();
		ClientProxy.vadeMecum = this;
		playerStats = player.getCapability(CapabilityList.VADEMECUM, null);
		if(playerStats != null && playerStats.getLastEntry().contains(":")) setEntry(VadeMecumEntry.getEntry(playerStats.getLastEntry().split(":")[0]), Integer.parseInt(playerStats.getLastEntry().split(":")[1]));
		else setEntry(ClientProxy.vadeMecumEntryList, 0);
		int i = (this.width - 192) / 2;
		this.button_forward = (VadeMecumGUI.ArrowButton) this.addButton(new VadeMecumGUI.ArrowButton(getButtonID(Button.Forward), i + 230, 195, true));
		this.button_back = (VadeMecumGUI.ArrowButton) this.addButton(new VadeMecumGUI.ArrowButton(getButtonID(Button.Back), i - 60, 195, false));
		button_entryBack = (VadeMecumGUI.OverlayButton) addButton(new VadeMecumGUI.OverlayButton(this, getButtonID(Button.EntryBack), vadeX + 18, vadeY + 8, true));
		button_credits = (VadeMecumGUI.OverlayButton) addButton(new VadeMecumGUI.OverlayButton(this, getButtonID(Button.Credits), vadeX + 358, vadeY + 8, false));
		this.updateButtons();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			this.mc.thePlayer.closeScreen();
		}
	}

	private void setEntry(VadeMecumEntry e, int page) {
		entry = e == null ? ClientProxy.vadeMecumEntryList : e;
		pageNumber = page > entry.getPageLength() ? entry.getPageLength() : page;
		e.init(this);
		this.updateButtons();
	}

	public void changeEntry(VadeMecumEntry e) {
		nextEntry = e;
	}

	@Override
	public void onGuiClosed() {
		ClientProxy.vadeMecum = null;
		String e = VadeMecumEntry.getEntry(entry);
		if(e != null) sendLastEntryPacket(e+":"+pageNumber);
	}

	@Override
	public void updateScreen() {
		if (nextEntry != null) {
			setEntry(nextEntry, 0);
			nextEntry = null;
		}
	}

	private void updateButtons() {
		if (button_forward != null) button_forward.visible = canDrawPage() ? pageNumber + 2 < entry.getPageLength() : false;
		if (button_back != null) button_back.visible = canDrawPage() ? pageNumber > 0 : false;
		if (button_entryBack != null) button_entryBack.visible = entry != ClientProxy.vadeMecumEntryList;
		if (button_credits != null) button_credits.visible = false;// entry == ClientProxy.vadeMecumEntryList.MAIN;
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
	 */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			switch (getButtonFromID(button.id)) {
				case Forward:
					pageNumber += 2;
					break;
				case Back:
					pageNumber -= 2;
					break;
				case EntryBack:
					setEntry(ClientProxy.vadeMecumEntryList, 0);
					break;
				case Credits:
					break;
				default:
					break;
			}
			// sendPacketUpdates(VadeMecumPacketHandler.RequestType.CATEGORY_ADD, IVadeMecumCapability.getCategoryID(IVadeMecumCapability.Category.TEST));
			// sendPacketUpdates(VadeMecumPacketHandler.RequestType.CATEGORY_CLEAR, 0);
			this.updateButtons();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (canDrawPage()) {
			entry.mouseClicked(this, pageNumber, mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(vadeX, vadeY, vadeW, vadeH, 0, 0, 256, 192);
		super.drawScreen(mouseX, mouseY, partialTicks);
		if (canDrawPage()) {
			entry.render(this, fontRendererObj, mouseX, mouseY, vadeX, vadeY, pageNumber);
		}
		if (button_entryBack != null && button_entryBack.visible) drawCenteredString(fontRendererObj, "Main", vadeX + 30, vadeY + 12, 0xFFFF00);
		if (button_credits != null && button_credits.visible) drawCenteredString(fontRendererObj, "Credits", vadeX + 360, vadeY + 12, 0xFFFF00);
		if (playerStats.getCurrentActive() != null) {

		}
		if(renderStackHover != null){
			renderToolTip(renderStackHover, mouseX, mouseY);
			renderStackHover = null;
		}
	}

	private boolean canDrawPage() {
		return playerStats != null && entry != null;
	}

	public static void drawCenteredStringNoShadow(FontRenderer render, String text, int x, int y, int color) {
		render.drawString(text, (x - render.getStringWidth(text) / 2), y, color);
	}

	@SideOnly(Side.CLIENT)
	static class ArrowButton extends GuiButton {
		private final boolean isForward;

		public ArrowButton(int id, int x, int y, boolean forward) {
			super(id, x, y, 23, 13, "");
			this.isForward = forward;
		}

		/**
		 * Draws this button to the screen.
		 */
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			if (this.visible) {
				boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(TEXTURES);
				int i = 0;
				int j = 192;

				if (flag) {
					i += 23;
				}

				if (!this.isForward) {
					j += 13;
				}
				this.drawTexturedModalRect(this.xPosition, this.yPosition, i, j, 23, 13);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	static class OverlayButton extends GuiButton {
		public static final ResourceLocation TEXTURE = new ResourceLocation(voidCraft.modid, "textures/gui/VadeMecum/edgeButton.png");
		private final boolean isForward;
		private final VadeMecumGUI parent;

		public OverlayButton(VadeMecumGUI gui, int id, int x, int y, boolean forward) {
			super(id, x, y, 20, 198, "");
			parent = gui;
			this.isForward = forward;
		}

		/**
		 * Draws this button to the screen.
		 */
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY) {
			if (this.visible) {
				boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(TEXTURE);
				int i = 0;
				int j = 0;

				if (flag) {
					j += 128;
				}

				if (!this.isForward) {
					i += 128;
				}
				parent.drawTexturedModalRect(this.xPosition, this.yPosition, width, height, i, j, 128, 128);
			}
		}
	}

	public void renderItemStack(ItemStack stack, int x, int y, int mx, int my) {
		if (itemRender != null){
            RenderHelper.enableGUIStandardItemLighting();
			itemRender.renderItemIntoGUI(stack, x, y);
			//drawCenteredString(fontRendererObj, ""+stack.stackSize, x, y, 0xFFFFFF);
			GlStateManager.disableDepth();
			if(stack.stackSize > 1) drawString(fontRendererObj, ""+stack.stackSize, x+11, y+9, 0xFFFFFF);
			GlStateManager.enableDepth();
			if(mx >= x && mx <= x+16 && my >= y && my <= y+16) renderStackHover = stack;
			RenderHelper.disableStandardItemLighting();
		}
	}

	public void drawTexturedModalRect(int x, int y, int width, int height, int textureX, int textureY, int textureW, int textureH) {
		GlStateManager.enableBlend();
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos((double) (x + 0), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + textureH) * 0.00390625F)).endVertex();
		vertexbuffer.pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + textureW) * 0.00390625F), (double) ((float) (textureY + textureH) * 0.00390625F)).endVertex();
		vertexbuffer.pos((double) (x + width), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + textureW) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).endVertex();
		vertexbuffer.pos((double) (x + 0), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).endVertex();
		tessellator.draw();
		GlStateManager.disableBlend();
	}

	public void sendPacketUpdates(VadeMecumPacketHandler.RequestType request, int objectID) {
		int pktType = ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.VADEMECUM);
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(pktType);
			VadeMecumPacketHandler.ClientToServerRequest(outputStream, player, request, objectID);
			FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(bos.buffer()), voidCraft.networkChannelName);
			voidCraft.channel.sendToServer(packet);
			outputStream.close();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void sendLastEntryPacket(String entry){
		if(entry.equals("")) return;
		int pktType = ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.VADEMECUM_LASTENTRY);
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(pktType);
			outputStream.writeUTF(entry);
			FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(bos.buffer()), voidCraft.networkChannelName);
			voidCraft.channel.sendToServer(packet);
			outputStream.close();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
