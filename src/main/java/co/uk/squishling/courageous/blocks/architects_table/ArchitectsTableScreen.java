package co.uk.squishling.courageous.blocks.architects_table;

import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelTileEntity;
import co.uk.squishling.courageous.util.Reference;
import co.uk.squishling.courageous.util.networking.ModPacketHandler;
import co.uk.squishling.courageous.util.networking.PacketPotterySelect;
import co.uk.squishling.courageous.util.rendering.SquareButton;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class ArchitectsTableScreen extends ContainerScreen<ArchitectsTableContainer> {

    private ResourceLocation GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/architects_table.png");
    private int selected;

    private HashMap<ItemStack, Pair<Integer, Integer>> ICONS = new HashMap<ItemStack, Pair<Integer, Integer>>();
    public ArrayList<Button> ABUTTONS = new ArrayList<Button>();

    public ArchitectsTableScreen(ArchitectsTableContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        for (Button button : ABUTTONS) button.render(mouseX, mouseY, partialTicks);

        GlStateManager.translatef(0.0F, 0.0F, 32.0F);
        for (ItemStack item : ICONS.keySet()) drawItemStack(item, ICONS.get(item).getLeft(), ICONS.get(item).getRight());

        renderHoveredToolTip(mouseX, mouseY);
        renderButtonToolTip(mouseX, mouseY);
    }

    public void addItemButton(ItemStack item) {
        int xOff = (width / 2 - xSize / 2) - 115;
        int yOff = (height / 2 - ySize / 2);

        int x = xOff + 23 * (ABUTTONS.size() % 5);
        int y = yOff + 23 * (ABUTTONS.size() / 5);

        ABUTTONS.add(new SquareButton(x, y, (button) -> {

        }));

        ICONS.put(item, new Pair<Integer, Integer>() {
            @Override
            public Integer setValue(Integer value) {
                return null;
            }

            @Override
            public Integer getLeft() {
                return x + 3;
            }

            @Override
            public Integer getRight() {
                return y + 3;
            }
        });
    }

    public void removeItemButton(int index) {
        ABUTTONS.remove(index);
        ICONS.remove(index);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        renderBackground();
        GlStateManager.color4f(1f, 1f, 1f, 1f);
        minecraft.getTextureManager().bindTexture(GUI);
        int relX = (width - xSize) / 2;
        int relY = (height - ySize) / 2;
        blit(relX, relY, 0, 0, xSize, ySize);
    }

    private void drawItemStack(ItemStack stack, int x, int y) {
        if (stack == null) return;
        this.itemRenderer.zLevel = 200.0F;
        this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRenderer.zLevel = 0.0F;
    }

    protected void renderButtonToolTip(int mouseX, int mouseY) {

        if (this.minecraft.player.inventory.getItemStack().isEmpty()) {
            for (ItemStack item : ICONS.keySet()) {
                if (item != null && mouseX >= ICONS.get(item).getLeft() && mouseY >= ICONS.get(item).getRight() && mouseX < ICONS.get(item).getLeft() + 16 && mouseY < ICONS.get(item).getRight() + 16) {
                    super.renderTooltip(item, mouseX, mouseY);
                }
            }
        }

    }

}