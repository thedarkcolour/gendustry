/*
 * Copyright (c) bdew, 2013 - 2017
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.sensors

import net.bdew.lib.Misc
import net.bdew.lib.gui._
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

abstract class SimpleGenericParameter(system: SensorSystem[_, _]) extends GenericSensorParameter(system) {
  @SideOnly(Side.CLIENT)
  def texture: Texture

  @SideOnly(Side.CLIENT)
  def textureColor = Color.white
}

abstract class SimpleGenericSensor[-T, +R](system: SensorSystem[T, R]) extends GenericSensorType[T, R](system) {
  def parameters: IndexedSeq[GenericSensorParameter]

  override lazy val defaultParameter = parameters.headOption.getOrElse(system.DisabledParameter)

  lazy val parameterMap = (parameters map (x => x.uid -> x)).toMap

  override def paramClicked(current: GenericSensorParameter, item: ItemStack, clickType: ClickType, button: Int, obj: T): GenericSensorParameter =
    if (item.isEmpty && button == 0 && clickType == ClickType.PICKUP)
      Misc.nextInSeq(parameters, current)
    else if (item.isEmpty && button == 1 && clickType == ClickType.PICKUP)
      Misc.prevInSeq(parameters, current)
    else
      current

  override def saveParameter(p: GenericSensorParameter, tag: NBTTagCompound) = tag.setString("param", p.uid)
  override def loadParameter(tag: NBTTagCompound) = parameterMap.getOrElse(tag.getString("param"), system.DisabledParameter)
  override def isValidParameter(p: GenericSensorParameter, obj: T) = parameters.contains(p)

  @SideOnly(Side.CLIENT)
  def texture: Texture

  @SideOnly(Side.CLIENT)
  def textureColor = Color.white

  @SideOnly(Side.CLIENT)
  override def drawSensor(rect: Rect, target: DrawTarget, obj: T): Unit = {
    target.drawTexture(rect, texture, textureColor)
  }

  @SideOnly(Side.CLIENT)
  override def drawParameter(rect: Rect, target: DrawTarget, obj: T, param: GenericSensorParameter): Unit = {
    param match {
      case x: SimpleGenericParameter => target.drawTexture(rect, x.texture, x.textureColor)
      case _ => target.drawTexture(rect, system.DisabledParameter.texture, system.DisabledParameter.textureColor)
    }

  }
}

