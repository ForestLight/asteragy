using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics.Animations {
    public class InterpolateController : ICollection<IInterpolate> {
        private readonly List<IInterpolate> collection = new List<IInterpolate>();

        public void Restart() {
            foreach(var item in collection) item.Restart();
        }

        public void Update(GraphicsDevice device, GameTime gameTime) {
            foreach(var item in collection) item.Update(device, gameTime);
        }

        #region ICollection<IInterpolate> メンバ

        public void Add(IInterpolate item) {
            collection.Add(item);
        }

        public void Clear() {
            collection.Clear();
        }

        public bool Contains(IInterpolate item) {
            return collection.Contains(item);
        }

        public void CopyTo(IInterpolate[] array, int arrayIndex) {
            collection.CopyTo(array, arrayIndex);
        }

        public int Count {
            get { return collection.Count; }
        }

        public bool IsReadOnly {
            get { return true; }
        }

        public bool Remove(IInterpolate item) {
            return collection.Remove(item);
        }

        #endregion

        #region IEnumerable<IInterpolate> メンバ

        public IEnumerator<IInterpolate> GetEnumerator() {
            return collection.GetEnumerator();
        }

        #endregion

        #region IEnumerable メンバ

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator() {
            return collection.GetEnumerator();
        }

        #endregion
    }
}
