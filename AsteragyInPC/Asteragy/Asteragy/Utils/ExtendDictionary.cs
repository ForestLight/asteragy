using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asteragy.Utils {
    public class ExtendDictionary<TKey, TValue> : Dictionary<TKey, TValue> {
        public new ExtendDictionary<TKey, TValue> Add(TKey key, TValue value) {
            base.Add(key, value);
            return this;
        }
    }
}
