package net.quiltservertools.interdimensional.gui.components

import eu.pb4.sgui.api.ClickType
import eu.pb4.sgui.api.elements.GuiElement
import eu.pb4.sgui.api.elements.GuiElementInterface
import eu.pb4.sgui.api.gui.SlotGuiInterface
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.SlotActionType
import net.quiltservertools.interdimensional.gui.CreateGuiHandler
import net.quiltservertools.interdimensional.text

interface LinkComponent {
    /*
    The stack shown to open the sub menu
     */
    fun getItemStack(): ItemStack
    fun createOptions()
    fun close()
    fun setResult(handler: CreateGuiHandler)

    fun createElement(): GuiElementInterface {
        return GuiElement(getItemStack().setCustomName(getItemStack().name)) {
                index: Int, type: ClickType?, action: SlotActionType?, gui: SlotGuiInterface ->
            this.createOptions()
        }
    }
}