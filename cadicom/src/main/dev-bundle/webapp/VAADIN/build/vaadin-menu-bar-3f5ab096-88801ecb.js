import{I as r,m as a,N as i,q as s,O as n,E as d}from"./indexhtml-5473386f.js";const m={tagName:"vaadin-menu-bar",displayName:"Menu Bar",elements:[{selector:"vaadin-menu-bar vaadin-menu-bar-button",displayName:"Buttons",properties:[r.backgroundColor,r.borderColor,r.borderWidth,r.borderRadius,{propertyName:"--lumo-button-size",displayName:"Size",editorType:a.range,presets:i.lumoSize,icon:"square"},s.paddingInline]},{selector:"vaadin-menu-bar vaadin-menu-bar-button vaadin-menu-bar-item",displayName:"Button labels",properties:[n.textColor,n.fontSize,n.fontWeight]},{selector:"vaadin-menu-bar-overlay::part(overlay)",displayName:"Overlay",properties:[r.backgroundColor,r.borderColor,r.borderWidth,r.borderRadius,r.padding]},{selector:"vaadin-menu-bar-overlay vaadin-menu-bar-item",displayName:"Menu Items",properties:[n.textColor,n.fontSize,n.fontWeight]}],async setupElement(t){t.overlayClass=t.getAttribute("class");const o=document.createElement("vaadin-menu-bar-item");t.items=[{component:o,children:[{text:"Sub item"}]}],await new Promise(e=>setTimeout(e,10))},async cleanupElement(t){t._close()},openOverlay(t){t.element.querySelector("vaadin-menu-bar-button").click();const o=t.element.shadowRoot.querySelector("vaadin-menu-bar-submenu");if(!o)return;const e=o.$.overlay;e&&(e._storedModeless=e.modeless,e.modeless=!0,document._themeEditorDocClickListener=d(o,e),document.addEventListener("click",document._themeEditorDocClickListener),document.documentElement.removeEventListener("click",o.__itemsOutsideClickListener))},hideOverlay(t){const o=t.element.shadowRoot.querySelector("vaadin-menu-bar-submenu");if(!o)return;const e=o.$.overlay;e&&(e.close(),e.modeless=e._storedModeless,delete e._storedModeless,document.removeEventListener("click",document._themeEditorDocClickListener),document.documentElement.addEventListener("click",o.__itemsOutsideClickListener),delete document._themeEditorDocClickListener)}};export{m as default};
