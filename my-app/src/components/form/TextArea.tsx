// src/components/form/TextArea.tsx
import React from 'react';
import { Field, ErrorMessage } from 'formik';
import "./TextArea.css";

interface TextAreaProps {
    label: string;
    name: string;
    rows?: number;
    cols?: number;
}

export const TextArea: React.FC<TextAreaProps> = ({ label, name, rows = 6, cols = 43 }) => {
    return (
        <>
            <div className="text-area-div">
                <label htmlFor={name}>{label}</label>
                <Field as="textarea" name={name} rows={rows} cols={cols} />
            </div>
            <ErrorMessage className="submit-field-error" name={name} component="span" />
        </>
    );
};

export default TextArea;