/*
 * Copyright (c) bdew, 2013 - 2017
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.power

import net.bdew.lib.Misc
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound

trait ItemPoweredBase extends Item {
  def maxCharge: Int
  def mjPerCharge: Int

  def getCharge(stack: ItemStack): Int = {
    if (!stack.hasTagCompound) setCharge(stack, 0)
    return Misc.clamp(stack.getTagCompound.getInteger("charge"), 0, maxCharge)
  }

  def useCharge(stack: ItemStack, uses: Int = 1, player: EntityLivingBase) {
    setCharge(stack, Misc.clamp(getCharge(stack) - uses * mjPerCharge, 0, maxCharge))
  }

  def hasCharges(stack: ItemStack) = getCharge(stack) >= mjPerCharge

  def setCharge(stack: ItemStack, charge: Int) {
    if (!stack.hasTagCompound) stack.setTagCompound(new NBTTagCompound())
    stack.getTagCompound.setInteger("charge", charge)
    updateDamage(stack)
  }

  def updateDamage(stack: ItemStack) {
    setDamage(stack, Misc.clamp((100 * (1 - getCharge(stack).toFloat / maxCharge)).round, 1, 100))
  }

  def stackWithCharge(charge: Int): ItemStack = {
    val n = new ItemStack(this)
    setCharge(n, charge)
    return n
  }

  override def setDamage(stack: ItemStack, damage: Int) = super.setDamage(stack, Misc.clamp(damage, 1, 100))
}
