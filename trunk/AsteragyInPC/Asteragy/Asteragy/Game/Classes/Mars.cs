﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using AsteragyData;

namespace Asteragy.Game.Classes
{
    public class Mars : AsterClass
    {
        public Mars(ContentManager content)
            : base(content.Load<AsterClassData>("Datas/Classes/mars"))
        {
        }
    }
}
