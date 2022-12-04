package dev.jdm.sortit.screen;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.jdm.sortit.SortIt;
import dev.jdm.sortit.block.SorterTypes;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SorterScreen extends HandledScreen<SorterScreenHandler> {

	private Identifier TEXTURE;
	SorterScreenHandler screenHandler;

	IconButtonWidget invertButton;
	SorterTypes type;

	public SorterScreen(SorterScreenHandler handler, PlayerInventory inventory, Text title, SorterTypes type) {
		super(handler, inventory, title);
		this.passEvents = false;
		this.backgroundHeight = 151;
		this.playerInventoryTitleY = this.backgroundHeight - 94;
		this.screenHandler = (SorterScreenHandler) handler;
		this.type = type; 
		this.TEXTURE = new Identifier(String.format("sortit:textures/gui/container/%s_sorter_gui.png", type.name));

	}

	@Override
	public void init() {
		super.init();

		invertButton = new IconButtonWidget(this.x + 15, this.y + 15, 112, 152, Text.of("OK"),
				TEXTURE);
		invertButton.setDisabled(this.handler.getSyncedInverted() == 1);
		this.addDrawableChild(invertButton);
	}

	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (this.width - this.backgroundWidth) / 2;
		int y = (this.height - this.backgroundHeight) / 2;

		this.drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

		List<Slot> filterSlots = this.handler.getFilteSlots();
		filterSlots.forEach((item)->{
			if (!item.hasStack()) {
				this.drawFilterIcon(matrices, item);
			}
		});
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

		invertButton.setDisabled(this.handler.getSyncedInverted() == 1);
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	private void drawFilterIcon(MatrixStack matrices, Slot slot ) {
		this.drawTexture(matrices, this.x +  slot.x, this.y + slot.y, this.backgroundWidth, 0, 16,
				16);
	}

	class IconButtonWidget
			extends BaseButtonWidget {
		private final int u;
		private final int v;

		public IconButtonWidget(int x, int y, int u, int v, Text message, Identifier Texture) {
			super(x, y, message, Texture);
			this.u = u;
			this.v = v;
		}

		@Override
		protected void renderExtra(MatrixStack matrices) {
			this.drawTexture(matrices, this.x + 2, this.y + 2, this.u, this.v, 18, 18);
		}

		@Override
		public void onPress() {
			PacketByteBuf buf = PacketByteBufs.create();
			buf.writeByte(!invertButton.isDisabled() ? 1 : 0);
			SorterScreen.this.client.getNetworkHandler()
					.sendPacket(new CustomPayloadC2SPacket(SortIt.INVERT_SORT_PACKET_ID, buf));

		}

	}

	static abstract class BaseButtonWidget
			extends PressableWidget {
		private boolean disabled;
		private Identifier Texture;

		protected BaseButtonWidget(int x, int y, Identifier Texture) {
			this(x, y, ScreenTexts.EMPTY, Texture);
		}

		protected BaseButtonWidget(int x, int y, Text message, Identifier Texture) {
			super(x, y, 22, 22, message);
			this.Texture = Texture;
		}

		@Override
		public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, this.Texture);
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			int j = 0;
			if (!this.active) {
				j += this.width * 2;
			} else if (this.disabled) {
				j += this.width * 2;
			} else if (this.isHovered()) {
				j += this.width * 3;
			}
			this.drawTexture(matrices, this.x, this.y, j, 151, this.width, this.height);
			this.renderExtra(matrices);
		}

		protected abstract void renderExtra(MatrixStack var1);

		public boolean isDisabled() {
			return this.disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		@Override
		public void appendNarrations(NarrationMessageBuilder builder) {
			this.appendDefaultNarrations(builder);
		}
	}

}
