using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Input;

namespace Asteragy.Input
{
	public enum KeyInputState
	{
		None,
		Up,
		Down,
		Left,
		Right,
		OK,
		Cancel,
	}
	public static class KeyInputListener
	{
		private static readonly Dictionary<Keys, KeyInputState> stateMap = new Dictionary<Keys, KeyInputState>()
		{
			{Keys.Up, KeyInputState.Up},
			{Keys.Down, KeyInputState.Down},
			{Keys.Left, KeyInputState.Left},
			{Keys.Right, KeyInputState.Right},
			{Keys.Enter, KeyInputState.OK},
			{Keys.C, KeyInputState.Cancel},
			{Keys.Z, KeyInputState.OK},
		};
		private static KeyboardState before;
		public static KeyInputState State { get; private set; }

		public static void Update()
		{
			KeyboardState after = Keyboard.GetState();
			foreach (var item in stateMap.Keys)
			{
				if (after.IsKeyDown(item) && before.IsKeyUp(item))
				{
					State = stateMap[item];
					before = after;
					return;
				}
			}
			State = KeyInputState.None;
			before = after;
		}
	}
}
