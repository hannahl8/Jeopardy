import React from 'react';
import {Field, ErrorMessage} from 'formik';
import "./TextInput.css"

interface TextInputProps {
    label: string;
    name: string;
    type?: string;
}

export const TextInput: React.FC<TextInputProps> = ({label, name, type = 'text'}) => {
    return (
        <>
            <div className="text-input-div">
                <label htmlFor={name}>{label}</label>
                <Field type={type} name={name}/>
            </div>
            <ErrorMessage className="submit-field-error" name={name} component="span"/>
        </>

    );
};

export default TextInput;