/*
 * Copyright (c) bdew, 2013 - 2017
 * https://github.com/bdew/bdlib
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.lib.rich

import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class RichWorld(val v: World) extends AnyVal {
  def changeBlockState(pos: BlockPos, flags: Int)(f: (IBlockState) => IBlockState): Unit = {
    v.setBlockState(pos, f(v.getBlockState(pos)), flags)
  }
}
