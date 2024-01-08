import{I as r,O as o,l as a,m as t,N as d}from"./indexhtml-2d5e0292.js";import{checkboxElement as l,checkedCheckboxElement as p}from"./vaadin-checkbox-4e68df64-276965db.js";import{cellProperties as e}from"./vaadin-grid-0a4791c2-6653452e.js";import{inputFieldProperties as i}from"./vaadin-text-field-0b3db014-e661e2f6.js";const v={tagName:"vaadin-grid-pro",displayName:"Grid Pro",elements:[{selector:"vaadin-grid-pro",displayName:"Root element",properties:[r.borderColor,r.borderWidth]},{selector:"vaadin-grid-pro::part(header-cell)",displayName:"Header row cell",properties:[o.textColor,{...o.fontSize,propertyName:"--lumo-font-size-s"},o.fontStyle,r.backgroundColor]},{selector:"vaadin-grid-pro::part(body-cell)",displayName:"Body cell",properties:e},{selector:"vaadin-grid-pro::part(even-row-cell)",displayName:"Cell in even row",properties:e},{selector:"vaadin-grid-pro::part(odd-row-cell)",displayName:"Cell in odd row",properties:e},{selector:"vaadin-grid-pro::part(selected-row-cell)",displayName:"Cell in selected row",properties:e},{selector:"vaadin-grid-pro vaadin-grid-cell-content > vaadin-checkbox::part(checkbox)",displayName:"Row selection checkbox",properties:l.properties},{selector:"vaadin-grid-pro vaadin-grid-cell-content > vaadin-checkbox[checked]::part(checkbox)",displayName:"Row selection checkbox (when checked)",properties:p.properties},{selector:"vaadin-grid-pro vaadin-grid-cell-content > vaadin-checkbox::part(checkbox)::after",displayName:"Row selection checkbox checkmark",properties:[a.iconColor]},{selector:"vaadin-grid-pro vaadin-grid-pro-edit-text-field",displayName:"Text field editor",properties:i},{selector:"vaadin-grid-pro vaadin-grid-pro-edit-checkbox",displayName:"Checkbox editor",properties:[{propertyName:"--vaadin-checkbox-size",displayName:"Checkbox size",defaultValue:"var(--lumo-font-size-l)",editorType:t.range,presets:d.lumoFontSize,icon:"square"}]},{selector:"vaadin-grid-pro vaadin-grid-pro-edit-select",displayName:"Select editor",properties:i}]};export{v as default};
