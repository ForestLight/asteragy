﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using AsteragyData;

namespace Asteragy.Game.Classes
{
    public class Neptune:AsterClass
    {
        public Neptune(ContentManager content)
            : base(content.Load<AsterClassData>("Datas/Classes/neptune"))
        {
        }
    }
}
