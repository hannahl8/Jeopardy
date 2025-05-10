import React from 'react';
import {Field, ErrorMessage} from 'formik';

interface SelectInputProps {
    label: string;
    name: string;
    options: { value: number; label: string }[];
}

export const SelectInput: React.FC<SelectInputProps> = ({label, name, options}) => {
    return (
        <div>
            <label htmlFor={name}>{label}</label>
            <Field as="select" name={name}>
                {options.map(option => (
                    <option key={option.value} value={option.value}>
                        {option.label}
                    </option>
                ))}
            </Field>
            <ErrorMessage className="submit-field-error" name={name} component="span"/>
        </div>
    );
};

export default SelectInput;
